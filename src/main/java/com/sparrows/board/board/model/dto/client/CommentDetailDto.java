package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.CommentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDetailDto {
    Long id;
    Long userId;
    String content;
    List<LikeDetailDto> likeDetailDtos;

    public CommentDetailDto(CommentEntity entity){
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.content = entity.getContent();
        this.likeDetailDtos = LikeDetailDto.from(entity.getLikes());
    }

    public static List<CommentDetailDto> from(List<CommentEntity> comments) {
        List<CommentDetailDto> dtos = new ArrayList<>();

        if(comments == null) return dtos;

        for(CommentEntity comment : comments){
            dtos.add(new CommentDetailDto(comment));
        }

        return dtos;
    }
}
