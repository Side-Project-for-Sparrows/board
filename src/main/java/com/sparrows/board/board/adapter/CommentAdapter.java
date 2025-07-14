package com.sparrows.board.board.adapter;

import com.sparrows.board.board.adapter.repository.CommentRepository;
import com.sparrows.board.board.factory.CommentFactory;
import com.sparrows.board.board.model.dto.client.CommentDetailDto;
import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.port.out.CommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CommentAdapter implements CommentPort {
    private final CommentRepository commentRepository;
    private final CommentFactory commentFactory;

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

    @Override
    public Page<CommentDetailDto> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<CommentEntity> commentEntityPage = commentRepository.findByPostId(postId,pageable);

        return commentFactory.buildCommentDetailDtos(commentEntityPage);
    }

}
