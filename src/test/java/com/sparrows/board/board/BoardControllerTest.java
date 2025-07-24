package com.sparrows.board.board;

import com.sparrows.board.board.factory.PostFactory;
import com.sparrows.board.board.model.dto.client.*;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import com.sparrows.board.board.port.in.LikeUsecase;
import com.sparrows.board.board.port.in.PostUsecase;
import com.sparrows.board.board.port.out.BoardPort;
import com.sparrows.board.board.port.out.BoardUserPort;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import com.sparrows.board.exception.handling.BoardNotFouncException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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
    private LikeUsecase likeUsecase;

    @Autowired
    private BoardPort boardPort;

    @Autowired
    private BoardUserPort boardUserPort;

    @Autowired
    private UserBoardRelationPort userBoardRelationPort;

    @Autowired
    private PostFactory postFactory;

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
        boardUserPort.save(user2, "user2", schoolId);
        boardUserPort.save(user3, "user3", schoolId);
    }

    @Test
    @DisplayName("게시판 생성 후 참여/탈퇴/글쓰기/좋아요 전체 흐름 테스트")
    void testBoardFlow() throws Exception {
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

        BoardJoinResponseDto join2 = boardUsecase.join(user2,
                BoardEntity.builder().id(savedBoard.getId()).enterCode(created.getEnterCode()).build());
        assertEquals("SUCCESS", join2.getResult());

        BoardJoinResponseDto join3 = boardUsecase.join(user3,
                BoardEntity.builder().id(savedBoard.getId()).enterCode(created.getEnterCode()).build());
        assertEquals("SUCCESS", join3.getResult());

        BoardWithdrawResponseDto withdraw2 = boardUsecase.withdrawBoard(savedBoard.getId(), user2, null);
        assertEquals("SUCCESS", withdraw2.getResult());

        PostCreateRequestDto post1Dto = PostCreateRequestDto.builder()
                .userId(user1)
                .boardId(savedBoard.getId())
                .title("유저1의 글")
                .content("내용1")
                .build();
        assertTrue(postUsecase.savePost(post1Dto, new MockMultipartFile[0]));

        PostCreateRequestDto postFailDto = PostCreateRequestDto.builder()
                .userId(user2)
                .boardId(savedBoard.getId())
                .title("유저2의 글")
                .content("내용2")
                .build();
        assertThrows(BoardNotFouncException.class, () ->
                postUsecase.savePost(postFailDto, new MockMultipartFile[0]));

        PostCreateRequestDto post3Dto = PostCreateRequestDto.builder()
                .userId(user3)
                .boardId(savedBoard.getId())
                .title("유저3의 글")
                .content("내용3")
                .build();
        assertTrue(postUsecase.savePost(post3Dto, new MockMultipartFile[0]));

        List<PostEntity> user3Posts = postUsecase.getAllPostsByUserId(user3);
        assertEquals(1, user3Posts.size());

        PostEntity post1 = user3Posts.stream().filter(p -> p.getUser().getId().equals(user1)).findFirst().orElse(null);
        assertNotNull(post1);
        PostDetailDto detail = postUsecase.getPostDetail(post1.getId());
        assertEquals("유저1의 글", detail.getTitle());

        assertTrue(likeUsecase.likePost(post1.getId(), user2));
        assertTrue(likeUsecase.likePost(post1.getId(), user2));
    }
}
