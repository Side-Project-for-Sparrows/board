package com.sparrows.board.schedule.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    // 수업 시작 시간 (ex 360 = 아침 6시)
    @Column(name = "start_time", nullable = false)
    private Integer startTime;

    @Column(name = "end_time", nullable = false)
    private Integer endTime;

    @Column(nullable = false)
    private Integer term;
}
