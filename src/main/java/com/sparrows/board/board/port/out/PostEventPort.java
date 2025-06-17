package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.dto.internal.PostSaveRequest;

public interface PostEventPort {
    void publishPostCreatedEvent(PostSaveRequest request);
}
