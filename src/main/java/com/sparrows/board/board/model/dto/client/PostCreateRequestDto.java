package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequestDto {
    Long userId;
    Integer boardId;
    String title;
    String content;

    public PostEntity to(){
        return PostEntity.builder()
                .userId(this.userId)
                .boardId(this.boardId)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
