package com.sparrows.board.board.model.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchRequest {
    private String domain;
    private String query;

    public static BoardSearchRequest from(String query){
        return new BoardSearchRequest("board", query);
    }
}
