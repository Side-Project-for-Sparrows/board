package com.sparrows.board.board.adapter;

import com.sparrows.board.board.adapter.repository.CommentRepository;
import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.port.out.CommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CommentAdapter implements CommentPort {
    private final CommentRepository commentRepository;
    @Override
    public boolean save(CommentEntity commentEntity) {
        CommentEntity result = commentRepository.save(commentEntity);
        return true;
    }

    @Override
    public List<CommentEntity> searchByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Optional<CommentEntity> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
