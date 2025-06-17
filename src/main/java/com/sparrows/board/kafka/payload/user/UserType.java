package com.sparrows.board.kafka.payload.user;

public enum UserType {
    ADMIN("admin"),
    OFFICIAL("official"),
    OUTSIDER("outsider");

    final String type;
    UserType(String type){
        this.type = type;
    }

    public String toString(){
        return this.type;
    }
}
