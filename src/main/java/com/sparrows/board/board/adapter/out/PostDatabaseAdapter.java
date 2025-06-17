package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.PostRepository;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.out.PostPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostDatabaseAdapter implements PostPort {
    @Autowired
    PostRepository postRepository;

    @Override
    public PostEntity save(PostEntity post) {
        return postRepository.save(post);
    }

    @Override
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<PostEntity> getPostsByBoardId(Long boardId) {
        return postRepository.findByBoardId(boardId);
    }

    @Override
    public Optional<PostEntity> findById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<PostEntity> getAllPostsByPostIds(List<Long> postIds) {
        return postRepository.findByIdIn(postIds);
    }

    @Override
    public List<PostEntity> getAllPostsByUserId(long id) {
        return postRepository.findByUserId(id);
    }


    @Override
    public Page<PostEntity> findRecentPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public PostEntity updatePost(PostEntity post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public long count() {
        return postRepository.count();
    }
}
