package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.LikeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LikeDetailDto {
    Long id;
    Long userId;
    Long postId;
    Long commentId;

    public LikeDetailDto(LikeEntity entity){
        this.id = entity.getId();
        this.userId = entity.getUserId();
        if(entity.getPost() != null){
            this.postId = entity.getPost().getId();
        }
        if(entity.getComment() != null){
            this.commentId = entity.getComment().getId();
        }
    }

    public static List<LikeDetailDto> from(List<LikeEntity> likes) {
        List<LikeDetailDto> dtos = new ArrayList<>();

        if(likes == null) return dtos;

        for(LikeEntity like : likes){
            dtos.add(new LikeDetailDto(like));
        }

        return dtos;
    }
}
