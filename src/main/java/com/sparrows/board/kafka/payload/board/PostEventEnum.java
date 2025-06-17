package com.sparrows.board.kafka.payload.board;

public enum PostEventEnum {
    PostCreated("PostCreated"),
    PostUpdated("PostUpdated"),
    PostDeleted("PostDeleted");

    String event;

    PostEventEnum(String event){
        this.event = event;
    }
}
