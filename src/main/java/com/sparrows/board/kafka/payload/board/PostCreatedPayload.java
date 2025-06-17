package com.sparrows.board.kafka.payload.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCreatedPayload {
    public int boardId;
    public long postId;
    public long writerId;
    public String title;
    public String content;
}