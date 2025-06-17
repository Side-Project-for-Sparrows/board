package com.sparrows.board.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.adapter.repository.BoardRepository;
import com.sparrows.board.board.adapter.repository.UserBoardRelationRepository;
import com.sparrows.board.board.model.dto.client.UpsertCalendarRequestDto;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Testcontainers
class CalendarControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("pass");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserBoardRelationRepository userBoardRelationRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Long createdCalendarId;

    private final Long userId = 7L;
    private static int boardId = 1;

    @BeforeEach
    void setupCalendarData() throws Exception {
        if (!userBoardRelationRepository.existsByUserIdAndBoardId(userId, boardId)) {
            BoardEntity board = new BoardEntity();
            board.setName("테스트 보드");
            board.setDescription("테스트 보드 게시판입니다");
            boardRepository.save(board);
            boardId = board.getId();

            UserBoardRelationEntity userBoard = new UserBoardRelationEntity();
            userBoard.setUserId(userId);

            board.setId(boardId);
            userBoard.setBoard(board);

            userBoardRelationRepository.save(userBoard);
        }

        UpsertCalendarRequestDto request = new UpsertCalendarRequestDto();
        request.setBoardId(boardId);
        request.setId(null);
        request.setTitle("테스트 일정");
        request.setMemo("테스트 메모");
        request.setUserId(userId);
        request.setTime(OffsetDateTime.of(2025, 6, 1, 10, 30, 0, 0, ZoneOffset.UTC));

        String json = objectMapper.writeValueAsString(request);

        System.out.println(">> 캘린더 생성 요청 JSON: " + json);

        mockMvc.perform(post("/calendar/upsert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        String query = String.format("?boardId=%d&userId=%d&year=2025&month=6", boardId, userId);
        String response = mockMvc.perform(get("/calendar/list" + query))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(">> 캘린더 리스트 응답: " + response);

        this.createdCalendarId = objectMapper.readTree(response).get(0).get("id").asLong();
        System.out.println(">> 생성된 캘린더 ID: " + createdCalendarId);
    }

    @Test
    void getCalendarList_ShouldReturnOk() throws Exception {
        String queryString = String.format("?boardId=%d&userId=%d&year=2025&month=6", boardId, userId);



        mockMvc.perform(get("/calendar/list" + queryString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    System.out.println(">> 일정 리스트 응답 바디: " + result.getResponse().getContentAsString());
                });
    }

    @Test
    void deleteCalendar_ShouldReturnOk() throws Exception {
        String queryString = String.format("?userId=%d&id=%d&boardId=%d", userId, createdCalendarId, boardId);

        mockMvc.perform(delete("/calendar" + queryString))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println(">> 삭제 요청 완료 - 상태 코드: " + result.getResponse().getStatus()));
    }
}
