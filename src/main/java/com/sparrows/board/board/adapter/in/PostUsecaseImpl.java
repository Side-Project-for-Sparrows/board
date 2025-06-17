package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.exception.handling.BoardNotFouncException;
import com.sparrows.board.board.exception.handling.PostNotFouncException;
import com.sparrows.board.board.model.dto.client.PostDetailDto;
import com.sparrows.board.board.model.dto.internal.PostSaveRequest;
import com.sparrows.board.board.model.dto.internal.PostSearchRequest;
import com.sparrows.board.board.model.entity.LikeEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.in.PostUsecase;
import com.sparrows.board.board.port.out.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostUsecaseImpl implements PostUsecase {
    private final PostPort postPort;
    private final LikePort likePort;
    private final PostImagePort postImagePort;
    private final PostSearchPort postSearchPort;
    private final PostEventPort postEventPort;
    private final UserBoardRelationPort userBoardRelationPort;


    @Transactional
    @Override
    public boolean savePost(PostEntity post, MultipartFile[] images) throws BoardNotFouncException {
        //post 저장 시도하려는 유저가 해당 board에 존재하는지 검증 필요
        isValid(post);

        //post 객체 저장
        postPort.save(post);

        //post_image 객체 저장
        int order = 0;
        try{
            for(MultipartFile imageFile: images){
                PostImageEntity image = PostImageEntity.createFromData(post, imageFile, order++);

                postImagePort.save(image);

                Path targetUrl = Paths.get( image.getAbsoluteUrl());
                File file = new File(String.valueOf(targetUrl));
                file.getParentFile().mkdirs(); // a/b/c/d 디렉토리 전부 생성
                imageFile.transferTo(file.toPath());
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }

        //post 생성 이벤트 발행
        postEventPort.publishPostCreatedEvent(PostSaveRequest.from(post));

        return true;
    }

    private void isValid(PostEntity post) throws BoardNotFouncException {
        Long userId = post.getUserId();
        Integer boardId = post.getBoardId();

        UserBoardRelationEntity userBoardRelationEntity = userBoardRelationPort.findByUserIdAndBoardId(userId,boardId);
        if(userBoardRelationEntity == null) throw new BoardNotFouncException();
    }

    @Override
    @Transactional
    public List<PostEntity> getAllPostsByUserId(long userId) {
        return postPort.getAllPostsByUserId(userId);
    }

    @Override
    @Transactional
    public List<PostEntity> getPostsByQuery(Integer boardId, String query) {
        List<Long> postIds = postSearchPort.search(PostSearchRequest.from(boardId, query)).getIds();
        return postPort.getAllPostsByPostIds(postIds);
    }

    @Override
    @Transactional
    public Page<PostEntity> getPosts(Pageable pageable) {
        return postPort.findRecentPosts(pageable);
    }

    @Override
    @Transactional
    public boolean likePost(Long postId, Long userId) {
        LikeEntity entity = likePort.findByPostIdAndUserId(postId,userId);

        //이전에 추천한 내역이 없을때는 새로 만들고 끝!
        if(entity == null){
            LikeEntity like = new LikeEntity();
            PostEntity post = new PostEntity();
            post.setId(postId);
            like.setPost(post);
            like.setUserId(userId);
            post.increaseLike();
            likePort.save(like);
            return true;
        }

        //like는 post 뿐만 아니라 거기 안의 코멘트에도 달 수 있기 때문에 commnet id가 널이어야함
        //이미 추천했는데 또 추천하면 추천 사라지는것으로 하자.
        if(entity.getComment() == null){
            likePort.delete(entity);
            PostEntity post = postPort.findById(postId).orElseThrow(PostNotFouncException::new);
            post.decreaseLike();
            return true;
        }

        //코멘트 관련 추천인경우 거부해야함.
        return false;
    }

    @Override
    @Transactional
    public PostDetailDto getPostDetail(Long postId) {
        PostEntity postEntity = postPort.findById(postId).orElseThrow(PostNotFouncException::new);
        return new PostDetailDto(postEntity);
    }
}
