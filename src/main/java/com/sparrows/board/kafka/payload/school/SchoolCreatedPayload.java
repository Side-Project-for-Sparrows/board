package com.sparrows.board.kafka.payload.school;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolCreatedPayload {
    public int schoolId;
    public String schoolName;
}