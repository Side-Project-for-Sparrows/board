package com.sparrows.board.kafka.payload.user;

import java.util.List;

public class UserUpdatedPayload{
    boolean isAdded;
    private List<Integer> boardIds;
    public int schoolId;
}
