package com.sparrows.board.board.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "calendar", indexes = {
        @Index(name = "idx_time_board", columnList = "time, board_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String memo;

    @Column(name = "board_id", nullable = false)
    private Integer boardId;

    @Column(nullable = false)
    private OffsetDateTime time;
}
