package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.factory.PostFactory;
import com.sparrows.board.board.model.dto.client.PostDetailDto;
import com.sparrows.board.board.model.dto.internal.PostSaveRequest;
import com.sparrows.board.board.model.dto.internal.PostSearchRequest;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.PostImageEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.in.PostUsecase;
import com.sparrows.board.board.port.out.*;
import com.sparrows.board.exception.handling.BoardNotFouncException;
import com.sparrows.board.exception.handling.PostNotFouncException;
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
    private final ImagePort imagePort;
    private final PostSearchPort postSearchPort;
    private final PostEventPort postEventPort;
    private final UserBoardRelationPort userBoardRelationPort;
    private final PostFactory postFactory;

    @Transactional
    @Override
    public boolean savePost(PostEntity post, MultipartFile[] images) throws Exception {
        //post 저장 시도하려는 유저가 해당 board에 존재하는지 검증 필요
        isValid(post);

        //post 객체 저장
        postPort.save(post);

        //post_image 객체 저장
        imagePort.save(post, images);

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
    public PostDetailDto getPostDetail(Long postId) {
        PostEntity postEntity = postPort.findById(postId).orElseThrow(PostNotFouncException::new);
        postEntity.increaseView();
        return postFactory.buildPostDetailDto(postEntity);
    }


}
