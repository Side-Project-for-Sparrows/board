package com.sparrows.board.board.port.in;

import com.sparrows.board.board.model.entity.CommentEntity;

public interface CommentUsecase {
    boolean saveComment(CommentEntity entity);
}
