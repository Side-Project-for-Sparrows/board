package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostImageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostImageDetailDto {
    Long id;
    Long postId;
    String url;

    public PostImageDetailDto(PostImageEntity entity){
        this.id = entity.getId();
        this.postId = entity.getPost().getId();
        this.url = entity.getUrl();
    }

    public static List<PostImageDetailDto> from(List<PostImageEntity> postImageEntities) {
        List<PostImageDetailDto> dtos = new ArrayList<>();

        if(postImageEntities == null) return dtos;

        for(PostImageEntity image: postImageEntities){
            dtos.add(new PostImageDetailDto(image));
        }

        return dtos;
    }
}
