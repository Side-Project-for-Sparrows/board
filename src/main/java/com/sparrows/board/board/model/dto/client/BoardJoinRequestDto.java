package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.BoardEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardJoinRequestDto {
    private Integer boardId;
    private Long userId;
    private String enterCode;

    public BoardEntity to(){
        return BoardEntity.builder()
                .id(boardId)
                .enterCode(enterCode)
                .build();
    }
}