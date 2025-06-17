package com.sparrows.board.board.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCalendarRequestDto {
    private Long userId;
    private Long id;
    private Integer boardId;
}
