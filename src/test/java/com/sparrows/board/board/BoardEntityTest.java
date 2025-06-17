package com.sparrows.board.board;

import com.sparrows.board.board.adapter.repository.BoardRepository;
import com.sparrows.board.board.adapter.repository.BoardUserRepository;
import com.sparrows.board.board.adapter.repository.UserBoardRelationRepository;
import com.sparrows.board.board.model.entity.BoardAuthority;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.BoardUserEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardEntityTest {
    @Autowired
    private BoardUserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserBoardRelationRepository userBoardRelationRepository;

    @Test
    void testUserBoardRelationAndFetchBoardsByUserId() {
        // 1. 유저 생성
        BoardUserEntity user = new BoardUserEntity();
        user.setId(-1L);
        user.setSchoolId(-2);
        userRepository.save(user);

        // 2. 보드 2개 생성
        BoardEntity boardEntity1 = new BoardEntity();
        boardEntity1.setName("First Board");
        boardEntity1.setDescription("First Content");
        boardEntity1.setIsPublic(true);
        boardEntity1.setSchoolId(user.getSchoolId());
        boardEntity1.setMadeBy(-1L);
        boardRepository.save(boardEntity1);

        BoardEntity boardEntity2 = new BoardEntity();
        boardEntity2.setName("Second Board");
        boardEntity2.setDescription("Second Content");
        boardEntity2.setIsPublic(true);
        boardEntity2.setSchoolId(user.getSchoolId());
        boardEntity2.setMadeBy(-1L);
        boardRepository.save(boardEntity2);

        // 3. UserBoard로 관계 맺기
        UserBoardRelationEntity ub1 = new UserBoardRelationEntity();
        ub1.setUserId(user.getId());
        ub1.setBoard(boardEntity1);
        ub1.setBoardAuthority(BoardAuthority.BOSS);
        userBoardRelationRepository.save(ub1);

        UserBoardRelationEntity ub2 = new UserBoardRelationEntity();
        ub2.setUserId(user.getId());
        ub2.setBoard(boardEntity2);
        ub2.setBoardAuthority(BoardAuthority.MANAGER);
        userBoardRelationRepository.save(ub2);

        // 4. userId로 board 목록 조회
        List<BoardEntity> boardEntities = userBoardRelationRepository.findBoardsByUserId(user.getId());

        // 5. 검증
        assertThat(boardEntities).hasSize(2);
        assertThat(boardEntities).extracting(BoardEntity::getName)
                .containsExactlyInAnyOrder("First Board", "Second Board");

        System.out.println("조회된 보드 목록:");
        boardEntities.forEach(b -> System.out.println(b.getName()));
    }
}
