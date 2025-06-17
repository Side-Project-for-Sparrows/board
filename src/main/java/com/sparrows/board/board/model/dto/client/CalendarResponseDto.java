package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.CalendarEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String title;
    private String memo;
    private Integer boardId;
    private OffsetDateTime time;

    public static CalendarResponseDto fromEntity(CalendarEntity entity) {
        return new CalendarResponseDto(entity.getId(), entity.getTitle(), entity.getMemo(), entity.getBoardId(), entity.getTime());
    }
}
