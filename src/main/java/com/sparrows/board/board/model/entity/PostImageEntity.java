package com.sparrows.board.board.model.entity;

import com.sparrows.board.common.model.BaseEntity;
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
    public static String baseUrl = System.getProperty("user.dir")+"/uploads/board/post/";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(nullable = false)
    private String url;

    public static PostImageEntity createFromData(PostEntity post, MultipartFile file, int order) throws Exception {

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new Exception();
        }

        String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));

        return PostImageEntity.builder()
                .post(post)
                .url(post.getId() +"/"+ order+prefix)
                .build();
    }

    public String getAbsoluteUrl(){
        return PostImageEntity.baseUrl+getUrl();
    }
}
