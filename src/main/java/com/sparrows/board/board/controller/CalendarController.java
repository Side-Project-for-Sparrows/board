package com.sparrows.board.board.controller;

import com.sparrows.board.board.model.dto.client.CalendarResponseDto;
import com.sparrows.board.board.model.dto.client.DeleteCalendarRequestDto;
import com.sparrows.board.board.model.dto.client.GetCalendarRequestDto;
import com.sparrows.board.board.model.dto.client.UpsertCalendarRequestDto;
import com.sparrows.board.board.port.in.CalendarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarUseCase calendarUseCase;

    @PostMapping("/upsert")
    public ResponseEntity<Void> upsert(@RequestBody UpsertCalendarRequestDto request) {
        calendarUseCase.upsertCalendar(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@ModelAttribute DeleteCalendarRequestDto request) {
        calendarUseCase.deleteCalendar(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CalendarResponseDto>> list(@ModelAttribute GetCalendarRequestDto request) {
        List<CalendarResponseDto> calendarList = calendarUseCase.getCalendarList(request);
        return ResponseEntity.ok(calendarList);
    }
}
