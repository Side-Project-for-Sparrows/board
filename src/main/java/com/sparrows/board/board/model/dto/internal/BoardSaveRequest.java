package com.sparrows.board.board.model.dto.internal;

import com.sparrows.board.board.model.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveRequest {
    Integer boardId;
    Integer schoolId;
    String name;
    String description;

    public static BoardSaveRequest from(BoardEntity entity){
        return new BoardSaveRequest(entity.getId(),entity.getSchoolId(), entity.getName(), entity.getDescription());
    }
}
