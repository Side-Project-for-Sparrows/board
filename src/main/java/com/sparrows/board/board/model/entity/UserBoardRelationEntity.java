package com.sparrows.board.board.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "user_board_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBoardRelationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    private BoardEntity board;

    @Enumerated(EnumType.STRING)
    private BoardAuthority BoardAuthority;

    private LocalDateTime banPeriod;   // 예: 정지 만료일 (혹은 null이면 정지되지 않음)
}