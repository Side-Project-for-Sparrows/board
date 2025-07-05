package com.sparrows.board.board.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content; // 게시물 내용, || 로 구분

    @Column
    @ColumnDefault("0")
    private int likeCount;

    @Column
    @ColumnDefault("0")
    private int commentCount;

    @Column
    @ColumnDefault("0")
    private int viewCount;

    @Column(nullable = false)
    private boolean isHidden;

    @Column(nullable = false)
    private int boardId;

    @Column(nullable = false)
    private long userId;

    @Column(unique = true)
    private String url; // slug

    @Column
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likes;

    @Column
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    @Column
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImageEntity> postImageEntities;

    public void update(String title, String content, boolean isHidden, String url) {
        this.title = title;
        this.content = content;
        this.isHidden = isHidden;
        this.url = url;
    }

    public void increaseView() {
        this.viewCount += 1;
    }

    public void increaseLike() {
        this.likeCount += 1;
    }

    public void decreaseLike() {
        this.likeCount -= 1;
    }

    public void increaseComment() {
        this.commentCount += 1;
    }

    public void decreaseComment() {
        this.commentCount -= 1;
    }
}
