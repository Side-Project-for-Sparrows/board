package com.sparrows.board.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.model.dto.client.PostCreateRequestDto;
import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.model.entity.UserBoardRelationEntity;
import com.sparrows.board.board.port.in.BoardUsecase;
import com.sparrows.board.board.port.out.BoardPort;
import com.sparrows.board.board.port.out.PostPort;
import com.sparrows.board.board.port.out.UserBoardRelationPort;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@Transactional
public class PostControllerTest {
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
    ObjectMapper objectMapper;

    @Autowired
    PostPort postPort;

    @Autowired
    UserBoardRelationPort userBoardRelationPort;

    @Autowired
    BoardPort boardPort;

    @Autowired
    BoardUsecase boardUsecase;

//    @MockBean
//    private BoardIndexClient boardIndexClient;

    @Test
    void 게시글_생성_API_성공() throws Exception {
        BoardEntity board = new BoardEntity();
        board.setSchoolId(4);
        board.setMadeBy(1L);
        board.setDescription("HIHI");
        board.setName("HIBOARD");
        board.setIsPublic(false);
        boardPort.save(board);

        //boardUsecasec.create


        UserBoardRelationEntity entity = new UserBoardRelationEntity();
        entity.setBoard(board);
        entity.setUserId(1L);

        userBoardRelationPort.save(entity);

        PostCreateRequestDto request = new PostCreateRequestDto();
        request.setUserId(1L);
        request.setBoardId(board.getId());
        request.setTitle("테스트 제목");
        request.setContent("테스트 내용");

        MockMultipartFile post = new MockMultipartFile(
                "post", // @RequestPart("post")와 일치해야 함
                "",
                "application/json",
                objectMapper.writeValueAsBytes(request)
        );

        MockMultipartFile image = new MockMultipartFile(
                "image", // @RequestPart("image")와 일치
                "test.jpg",
                "image/jpeg",
                "dummy".getBytes()
        );

        //when(boardIndexClient.saveBoard(any())).thenReturn(mock(BoardSaveResponse.class));

        mockMvc.perform(multipart("/post") // URL은 실제 컨트롤러 매핑에 맞춰 수정
                .file(post)
                .file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        List<PostEntity> posts = postPort.findAll();
        for(PostEntity postT: posts){
            System.out.println(postT.getContent());
        }
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("테스트 제목");
        assertThat(posts.get(0).getContent()).isEqualTo("테스트 내용");
    }
}