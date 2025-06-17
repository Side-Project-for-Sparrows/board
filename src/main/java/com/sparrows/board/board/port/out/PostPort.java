package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostPort {
    PostEntity save(PostEntity post);

    List<PostEntity> findAll();

    List<PostEntity> getPostsByBoardId(Long boardId);

    Optional<PostEntity> findById(long id);

    List<PostEntity> getAllPostsByPostIds(List<Long> postIds);

    List<PostEntity> getAllPostsByUserId(long id);

    Page<PostEntity> findRecentPosts(Pageable pageable);

    PostEntity updatePost(PostEntity post);

    void deletePost(long id);

    long count();
}
