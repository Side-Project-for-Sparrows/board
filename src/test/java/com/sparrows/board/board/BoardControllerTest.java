package com.sparrows.board.board;

import com.sparrows.board.board.exception.handling.BoardNotFouncException;
import com.sparrows.board.board.model.dto.client.BoardCreateResponseDto;
import com.sparrows.board.board.model.dto.client.BoardJoinResponseDto;
import com.sparrows.board.board.model.dto.client.BoardWithdrawResponseDto;
import com.sparrows.board.board.model.dto.client.PostDetailDto;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import com.sparrows.board.board.port.in.PostUsecase;
import com.sparrows.board.board.port.out.BoardPort;
import com.sparrows.board.board.port.out.BoardUserPort;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@DisplayName("게시판 생성 - 조인 - 탈퇴 통합 테스트")
class BoardControllerTest {

    @Autowired
    private BoardUsecase boardUsecase;

    @Autowired
    private BoardPort boardPort;

    @Autowired
    private BoardUserPort boardUserPort;

    @Autowired
    private UserBoardRelationPort userBoardRelationPort;

    @Autowired
    private PostUsecase postUsecase;

    private final Long user1 = 1L;
    private final Long user2 = 2L;
    private final Long user3 = 3L;
    private final Integer schoolId = 202;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @BeforeEach
    void setUp() {
        boardUserPort.save(user1, "user1", schoolId);
        boardUserPort.save(user2,"user2", schoolId);
        boardUserPort.save(user3, "user3",schoolId);
    }

    @Test
    @DisplayName("게시판 생성 후 참여/탈퇴/글쓰기/좋아요 전체 흐름 테스트")
    void testBoardFlow() throws Exception {
        // 1. 유저1이 게시판 생성
        BoardEntity board = BoardEntity.builder()
                .name("통합 테스트 게시판")
                .schoolId(schoolId)
                .madeBy(user1)
                .isPublic(true)
                .description("통합 흐름 테스트")
                .build();
        BoardCreateResponseDto created = boardUsecase.createNewBoard(user1, board);
        assertEquals("SUCCESS", created.getResult());

        BoardEntity savedBoard = boardPort.findByNameAndSchoolId(board.getName(), schoolId);
        assertNotNull(savedBoard);

        // 2. 유저2, 유저3 참여
        BoardJoinResponseDto join2 = boardUsecase.join(user2,
                BoardEntity.builder().id(savedBoard.getId()).enterCode(created.getEnterCode()).build());
        assertEquals("SUCCESS", join2.getResult());

        BoardJoinResponseDto join3 = boardUsecase.join(user3,
                BoardEntity.builder().id(savedBoard.getId()).enterCode(created.getEnterCode()).build());
        assertEquals("SUCCESS", join3.getResult());

        // 3. 유저2 탈퇴
        BoardWithdrawResponseDto withdraw2 = boardUsecase.withdrawBoard(savedBoard.getId(), user2, null);
        assertEquals("SUCCESS", withdraw2.getResult());

        // 4. 유저1 글쓰기 (성공)
        PostEntity post1 = new PostEntity();
        post1.setUserId(user1);
        post1.setBoardId(savedBoard.getId());
        post1.setTitle("유저1의 글");
        post1.setContent("내용1");

        assertTrue(postUsecase.savePost(post1, new MultipartFile[0]));

        // 5. 유저2 글쓰기 (실패 예상)
        PostEntity postFail = new PostEntity();
        postFail.setUserId(user2);
        postFail.setBoardId(savedBoard.getId());
        postFail.setTitle("유저2의 글");
        postFail.setContent("내용2");

        assertThrows(BoardNotFouncException.class, () ->
                postUsecase.savePost(postFail, new MultipartFile[0]));

        // 6. 유저3 글쓰기 (성공)
        PostEntity post3 = new PostEntity();
        post3.setUserId(user3);
        post3.setBoardId(savedBoard.getId());
        post3.setTitle("유저3의 글");
        post3.setContent("내용3");

        assertTrue(postUsecase.savePost(post3, new MultipartFile[0]));

        // 7. 유저3 게시글 개수 확인
        List<PostEntity> user3Posts = postUsecase.getAllPostsByUserId(user3);
        assertEquals(1, user3Posts.size());

        // 8. 게시글 상세 조회
        PostDetailDto detail = postUsecase.getPostDetail(post1.getId());
        assertEquals("유저1의 글", detail.getTitle());

        // 9. 유저2가 유저1 글에 좋아요 (되긴 해야 함. 탈퇴와 무관)
        assertTrue(postUsecase.likePost(post1.getId(), user2));

        // 10. 유저2가 다시 좋아요 (취소됨)
        assertTrue(postUsecase.likePost(post1.getId(), user2));
    }
}

