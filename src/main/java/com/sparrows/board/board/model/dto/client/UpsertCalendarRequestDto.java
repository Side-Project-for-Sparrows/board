package com.sparrows.board.board.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCalendarRequestDto {
    private Long userId;
    private Long id;
    private String title;
    private String memo;
    private Integer boardId;
    private OffsetDateTime time;
}
