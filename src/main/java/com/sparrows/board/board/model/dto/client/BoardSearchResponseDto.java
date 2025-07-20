package com.sparrows.board.board.model.dto.client;

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
    String description;
    Boolean isBoss;
    Boolean isPublic;
}
