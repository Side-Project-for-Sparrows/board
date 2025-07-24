package com.sparrows.board.board.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequestDto {
    Long userId;
    Integer boardId;
    String title;
    String content;
}
