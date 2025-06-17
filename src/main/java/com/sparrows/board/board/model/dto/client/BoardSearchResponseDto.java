package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardSearchResponseDto {
    Integer boardId;
    String name;
    Integer schoolId;
    Boolean isPublic;
    String description;

    public static BoardSearchResponseDto from(BoardEntity board){
        return BoardSearchResponseDto.builder()
                .boardId(board.getId())
                .schoolId(board.getSchoolId())
                .name(board.getName())
                .description(board.getDescription())
                .isPublic(board.getIsPublic())
                .build();
    }
}
