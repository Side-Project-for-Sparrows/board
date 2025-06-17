package com.sparrows.board.kafka.payload.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardCreatedPayload {
    public int boardId;
    public int schoolId;
    public String name;
    public String description;
}