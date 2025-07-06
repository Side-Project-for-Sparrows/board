package com.sparrows.board.board.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_user_cqrs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"school_id", "user_id"})
        }
)
public class BoardUserEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer schoolId;
}
