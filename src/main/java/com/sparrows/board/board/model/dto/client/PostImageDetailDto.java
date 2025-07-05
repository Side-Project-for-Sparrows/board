package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostImageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostImageDetailDto {
    String key;

    public PostImageDetailDto(PostImageEntity entity){
        this.key = entity.getKey();
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
