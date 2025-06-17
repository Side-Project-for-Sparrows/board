package com.sparrows.board.board.model.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest {
    private String domain;
    private Integer boardId;
    private String query;

    public static PostSearchRequest from(Integer boardId, String query){
        return new PostSearchRequest("post", boardId, query);
    }
}
