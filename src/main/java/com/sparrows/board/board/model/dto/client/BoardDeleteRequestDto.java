package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.BoardEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDeleteRequestDto {
    Long userId;
    Integer boardId;

    public BoardEntity convert(){
        return BoardEntity.builder()
                .id(this.boardId)
                .madeBy(this.userId)
                .build();
    }
}
