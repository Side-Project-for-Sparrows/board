package com.sparrows.board.board.adapter.in;

import com.sparrows.board.board.factory.BoardFactory;
import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.dto.internal.BoardSaveRequest;
import com.sparrows.board.board.model.dto.internal.BoardSearchRequest;
import com.sparrows.board.board.model.entity.BoardAuthority;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import com.sparrows.board.board.port.out.*;
import com.sparrows.board.exception.handling.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardUsecaseImpl implements BoardUsecase {
    private final BoardPort boardPort;
    private final BoardEventPort boardEventPort;
    private final BoardUserPort boardUserPort;
    private final BoardSearchPort boardSearchPort;
    private final UserBoardRelationPort userBoardRelationPort;
    private final BoardFactory boardFactory;

    @Override
    @Transactional
    public BoardCreateResponseDto createNewBoard(Long userId, BoardEntity boardEntity) {

        // 1. 유저가 게시판을 만들때는 무조건 private에 entercode 생성 필요
        if (userId != null && userId != 0 && boardEntity.getSchoolId() != null && boardEntity.getSchoolId() != 0) {
            if(!boardUserPort.existsByUserIdAndSchoolId(userId, boardEntity.getSchoolId()))
                throw new FailUserNotFoundException();

            //boardEntity.setSchoolId(null);
            if(!boardEntity.getIsPublic()) boardEntity.updateEnterCode();
        }

        //학교 생성시 생성되는 게시판들은 private + 엔터코드 생성 필요 없음
        if(userId == null || userId == 0L){
            if(boardEntity.getSchoolId() == null) throw new IllegalArgumentException("SCHOOL ID : NULL");
            boardEntity.setIsPublic(false);
        }

        // 2. 같은 학교 내에 동일한 이름의 게시판이 있는지 검증
        if (boardPort.existsByNameAndSchoolId(boardEntity.getName(), boardEntity.getSchoolId())) {
            throw new SchoolAlreayExistException();
        }

        // 3. 게시판 생성
        BoardEntity savedBoard = boardPort.save(boardEntity);

        //user-board 에서 boss로 임명
        setUserBoardRelation(userId, boardEntity, BoardAuthority.BOSS);

        // 4. 검색용 데이터 저장 - 게시판 생성되면 그냥 카프카 퍼블리쉬만 하자
        boardEventPort.publishBoardCreateEvent(BoardSaveRequest.from(savedBoard));

        return new BoardCreateResponseDto(true, savedBoard.getEnterCode());
    }

    @Override
    @Transactional
    public BoardUpdateResponseDto validateAndUpdateBoard(Long userId, BoardEntity boardEntity) {
        // 1. 유저가 해당 학교에 속하는지 검증
        if (boardEntity.getSchoolId() != null && boardEntity.getSchoolId() != 0) {
            //유저가 속하지 않았으면 에러
            if(!boardUserPort.existsByUserIdAndSchoolId(userId, boardEntity.getSchoolId()))
                throw new FailUserNotFoundException();

            //유저권한이 Boss가 아니라면 에러
            UserBoardRelationEntity entity = userBoardRelationPort.findByUserIdAndBoardId(userId, boardEntity.getId());
            if(!BoardAuthority.BOSS.equals(entity.getBoardAuthority())){
                throw new AccessDeniedException();
            }
        }

        update(boardEntity);
        return new BoardUpdateResponseDto(true);
    }

    @Override
    @Transactional
    public BoardWithdrawResponseDto withdrawBoard(Integer boardId, Long userId, Long transferToUserId) {
        // 1. 유저가 해당 보드에 참여 중인지 확인
        UserBoardRelationEntity currentRelation = userBoardRelationPort.findByUserIdAndBoardId(userId, boardId);

        if (currentRelation == null) throw new BoardNotFouncException();

        //일반 참여자면 바로 삭제
        if (currentRelation.getBoardAuthority() != BoardAuthority.BOSS) {
            userBoardRelationPort.deleteByBoardIdAndUserId(boardId, userId);
            return new BoardWithdrawResponseDto(true);
        }

        // 2. 보스인 경우 양도 유저 확인
        List<UserBoardRelationEntity> userBoardRelationEntities = userBoardRelationPort.findUsersByBoardId(boardId);

        //보스 한명만 있는경우 게시판까지 삭제
        if(userBoardRelationEntities.size() == 1){
            userBoardRelationPort.deleteByBoardIdAndUserId(boardId,userId);
            boardPort.deleteById(boardId);
            return new BoardWithdrawResponseDto(true);
        }

        if (transferToUserId == null) throw new IllegalArgumentException("보스는 탈퇴 시 권한을 다른 유저에게 양도해야 합니다.");
        UserBoardRelationEntity transferTarget = userBoardRelationPort.findByUserIdAndBoardId(transferToUserId, boardId);

        if (transferTarget == null) throw new BoardUserNotFoundException();

        // 3. 양도 대상에게 권한 넘김
        transferTarget.setBoardAuthority(BoardAuthority.BOSS);
        userBoardRelationPort.save(transferTarget);

        // 4. 현재 유저는 게시판에서 탈퇴 (삭제 또는 권한 제거)
        userBoardRelationPort.deleteByBoardIdAndUserId(boardId,userId);
        return new BoardWithdrawResponseDto(true);
    }

    @Transactional
    public BoardJoinResponseDto join(Long userId, BoardEntity board) {
        BoardEntity realBoard = boardPort.findById(board.getId()).orElseThrow(BoardNotFouncException::new);
        if(!realBoard.getIsPublic() && !realBoard.getEnterCode().equals(board.getEnterCode())) throw new AccessDeniedException();

        UserBoardRelationEntity entity = userBoardRelationPort.findByUserIdAndBoardId(userId,board.getId());
        if(entity != null) throw new UserAlreadyJoinedException();

        setUserBoardRelation(userId,realBoard,BoardAuthority.CITIZEN);
        return new BoardJoinResponseDto(true);
    }

    @Override
    public BoardMemberResponseDto getMembers(Long userId, Integer boardId) {
        //userId가 boss일때만 허용해야함
        validateIfRequesterisBoss(userId, boardId);

        return  boardFactory.buildBoardMember(userId,boardId);
    }

    private void validateIfRequesterisBoss(Long userId, Integer boardId) {
        UserBoardRelationEntity entity = userBoardRelationPort.findByUserIdAndBoardId(userId,boardId);
        if(entity == null) throw new BoardUserNotFoundException();
        if(!BoardAuthority.BOSS.equals(entity.getBoardAuthority())) throw new AccessDeniedException();
    }

    public void setUserBoardRelation(Long userId, Integer boardId, Integer schoolId, BoardAuthority authority) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardId);
        boardEntity.setSchoolId(schoolId);
        setUserBoardRelation(userId, boardEntity, authority);
    }

    // 외부에서 넘김 (재사용 목적)
    public void setUserBoardRelation(Long userId, BoardEntity boardEntity, BoardAuthority authority) {
        UserBoardRelationEntity entity = UserBoardRelationEntity.builder()
                .board(boardEntity)
                .BoardAuthority(authority)
                .userId(userId)
                .build();

        userBoardRelationPort.save(entity);
    }

    @Override
    public BoardEntity update(BoardEntity newBoard) {
        BoardEntity board = boardPort.findById(newBoard.getId()).orElseThrow(BoardNotFouncException::new);

        if(newBoard.getName() != null){
            board.setName(newBoard.getName());
        }
        if(newBoard.getDescription() != null){
            board.setDescription(newBoard.getDescription());
        }
        if(newBoard.getIsPublic() != null){
            board.setIsPublic(newBoard.getIsPublic());
        }
        return board;
    }

    @Override
    public List<BoardSearchResponseDto> searchAllBoardsByUserId(Long userId) {
         List<BoardEntity> boardEntities = userBoardRelationPort.findByUserId(userId);

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();
        for(BoardEntity entity: boardEntities){
            boardSearchResponseDtos.add(boardFactory.buildBoardSearchResponse(userId, entity.getId()));
        }

        return boardSearchResponseDtos;
    }


    @Override
    public void delete(int id) {
        boardPort.deleteById(id);
    }

    @Override
    public List<BoardSearchResponseDto> searchBoardByQuery(Long userId, String query) {
        List<Long> boardIds = boardSearchPort.search(BoardSearchRequest.from(query)).getIds();

        List<BoardEntity> boards = new ArrayList<>();
        for(Long boardId: boardIds){
            Optional<BoardEntity> board = boardPort.findById(boardId.intValue());
            if(board.isEmpty()) continue;
            boards.add(board.get());
        }

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();
        for(BoardEntity entity: boards){
            boardSearchResponseDtos.add(boardFactory.buildBoardSearchResponse(userId, entity.getId()));
        }

        return boardSearchResponseDtos;
    }
}
