package com.sparrows.board.board.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_image")
public class PostImageEntity extends BaseEntity {
    private static String PREFIX = "POST_IMAGE_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(nullable = false)
    private String key;

    public static String createKey(PostEntity post, int order) {
        return PREFIX+post.getId()+"_"+order;
    }

    public PostImageEntity(PostEntity post, String key){
        this.post = post;
        this.key = key;
    }

}
