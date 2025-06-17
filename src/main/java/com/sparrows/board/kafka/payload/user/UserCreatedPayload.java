package com.sparrows.board.kafka.payload.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreatedPayload{
    public long userId;
    public int schoolId;
    public UserType userType;
}
