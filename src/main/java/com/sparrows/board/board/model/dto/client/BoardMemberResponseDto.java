package com.sparrows.board.board.model.dto.client;

import com.sparrows.board.board.model.entity.BoardUserEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardMemberResponseDto {
    List<BoardMember> members;

    public BoardMemberResponseDto() {
        this.members = new ArrayList<>();
    }

    @Getter
    static class BoardMember{
        Long userId;
        String nickname;

        public BoardMember(BoardUserEntity user){
            this.userId = user.getId();
            this.nickname = user.getNickname();
        }
    }

    public void addMember(BoardUserEntity user){
        this.members.add(new BoardMember(user));
    }
}