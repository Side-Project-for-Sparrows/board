package com.sparrows.board.board.model.entity;

import com.sparrows.board.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = true)
    private Integer schoolId;

    @Column
    private String notice;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private Long madeBy;

    @Column(nullable = false)
    private String description;

    @Column
    private String enterCode;

    @Column
    private LocalDateTime entercodeChangedAt;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBoardRelationEntity> userBoardRelationEntityList;

    public void updateEnterCode(){
        this.enterCode = UUID.randomUUID().toString();
        this.entercodeChangedAt = LocalDateTime.now();
    }
}
