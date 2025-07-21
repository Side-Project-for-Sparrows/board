package com.sparrows.board.board.factory;

import com.sparrows.board.board.model.dto.client.BoardMemberResponseDto;
import com.sparrows.board.board.model.dto.client.BoardSearchResponseDto;
import com.sparrows.board.board.model.entity.BoardAuthority;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.BoardUserEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.out.BoardPort;
import com.sparrows.board.board.port.out.BoardUserPort;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BoardFactory {
    private final BoardUserPort boardUserPort;
    private final UserBoardRelationPort userBoardRelationPort;
    private final BoardPort boardPort;

    public BoardMemberResponseDto buildBoardMember(Long userId, Integer boardId){

        List<UserBoardRelationEntity> userBoardRelationEntities = userBoardRelationPort.findUsersByBoardId(boardId);
        BoardMemberResponseDto dto = new BoardMemberResponseDto();

        for(UserBoardRelationEntity entity: userBoardRelationEntities){
            BoardUserEntity user = boardUserPort.findById(entity.getUserId()).orElseThrow();
            dto.addMember(user);
        }

        return dto;
    }

    public BoardSearchResponseDto buildRelatedBoardSearchResponse(Long userId, Integer boardId){

        UserBoardRelationEntity userboard = userBoardRelationPort.findByUserIdAndBoardId(userId,boardId);
        BoardEntity board = userboard.getBoard();

            return BoardSearchResponseDto.builder()
                    .boardId(board.getId())
                    .schoolId(board.getSchoolId())
                    .name(board.getName())
                    .description(board.getDescription())
                    .isBoss(BoardAuthority.BOSS.equals(userboard.getBoardAuthority()))
                    .isPublic(board.getIsPublic())
                    .build();

    }

    public BoardSearchResponseDto buildBoardSearchResponse(Integer boardId){
        BoardEntity board = boardPort.findById(boardId).orElseThrow();

        return BoardSearchResponseDto.builder()
                .boardId(board.getId())
                .schoolId(board.getSchoolId())
                .name(board.getName())
                .description(board.getDescription())
                .isBoss(false)
                .isPublic(board.getIsPublic())
                .build();

    }
}
