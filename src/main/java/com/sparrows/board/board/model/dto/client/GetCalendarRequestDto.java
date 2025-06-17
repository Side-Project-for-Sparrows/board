package com.sparrows.board.board.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCalendarRequestDto {
    private Long userId;
    private Integer boardId;
    private Integer year;
    private Integer month;
}
