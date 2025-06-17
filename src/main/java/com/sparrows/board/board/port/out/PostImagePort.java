package com.sparrows.board.board.port.out;

import com.sparrows.board.board.model.entity.PostImageEntity;

public interface PostImagePort {
    PostImageEntity save(PostImageEntity postImageEntity);
}
