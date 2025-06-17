package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequestDto {
    Long userId;
    String name;
    Integer schoolId;
    Boolean isPublic;
    String description;

    public BoardEntity convert(){
        BoardEntity board = BoardEntity.builder()
                .name(this.name)
                .description(this.description)
                .isPublic(this.isPublic)
                .schoolId(this.schoolId)
                .madeBy(this.userId)
                .build();
        board.updateEnterCode();

        return board;
    }
}
