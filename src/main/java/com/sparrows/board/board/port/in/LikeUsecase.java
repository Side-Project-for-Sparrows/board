package com.sparrows.board.board.port.in;

public interface LikeUsecase {
    public boolean likePost(Long requesterId, Long postId);
    public boolean likeComment(Long requesterId, Long postId, Long commentId);
}
