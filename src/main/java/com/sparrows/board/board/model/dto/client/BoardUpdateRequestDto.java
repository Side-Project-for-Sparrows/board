package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.BoardEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardUpdateRequestDto {
    Long userId;
    String name;
    Integer boardId;
    Boolean isPublic;
    String description;

    public BoardEntity convert(){
        return BoardEntity.builder()
                .id(this.boardId)
                .name(this.name)
                .description(this.description)
                .isPublic(this.isPublic)
                .madeBy(this.userId)
                .build();
    }
}
