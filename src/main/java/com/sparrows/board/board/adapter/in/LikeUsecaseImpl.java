package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.model.entity.LikeEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.in.CommentUsecase;
import com.sparrows.board.board.port.in.LikeUsecase;
import com.sparrows.board.board.port.out.CommentPort;
import com.sparrows.board.board.port.out.LikePort;
import com.sparrows.board.board.port.out.PostPort;
import com.sparrows.board.exception.handling.BoardNotFouncException;
import com.sparrows.board.exception.handling.CommentNotFouncException;
import com.sparrows.board.exception.handling.PostNotFouncException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LikeUsecaseImpl implements LikeUsecase {
    private final LikePort likePort;
    private final PostPort postPort;
    private final CommentPort commentPort;

    @Transactional
    @Override
    public boolean likePost(Long requesterId, Long postId) {
        Optional<LikeEntity> optionalLike = likePort.findByPostIdAndUserId(postId, requesterId);

        if (optionalLike.isPresent()) {
            LikeEntity like = optionalLike.get();
            likePort.delete(like);
            PostEntity post = postPort.findById(postId).orElseThrow(PostNotFouncException::new);
            post.decreaseLike();
            return false;
        } else {
            PostEntity post = postPort.findById(postId).orElseThrow(PostNotFouncException::new);
            LikeEntity like = LikeEntity.builder()
                    .userId(requesterId)
                    .post(post)
                    .build();
            likePort.save(like);
            post.increaseLike();
            return true;
        }
    }


    @Transactional
    @Override
    public boolean likeComment(Long requesterId, Long postId, Long commentId) {
        Optional<LikeEntity> optionalLike = likePort.findByCommentIdAndUserId(commentId, requesterId);
        CommentEntity comment = commentPort.findById(commentId)
                .orElseThrow(CommentNotFouncException::new);

        if (optionalLike.isPresent()) {
            likePort.delete(optionalLike.get());
            comment.decreaseLike();  // ← 도메인 모델에게 책임 위임
            return false;
        } else {
            LikeEntity like = LikeEntity.builder()
                    .userId(requesterId)
                    .comment(comment)
                    .post(comment.getPost())
                    .build();
            likePort.save(like);
            comment.increaseLike();
            return true;
        }
    }
}