package com.sparrows.board.board.port.in;

import com.sparrows.board.board.model.dto.client.PostDetailDto;
import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostUsecase {
    boolean savePost(PostEntity post, MultipartFile[] images) throws Exception;

    //유저와 연관된 모든 게시글 가져오기
    List<PostEntity> getAllPostsByUserId(long userId);

    List<PostEntity> getPostsByQuery(Integer boardId, String query);

    Page<PostEntity> getPosts(Pageable pageable);

    PostDetailDto getPostDetail(Long postId);
}
