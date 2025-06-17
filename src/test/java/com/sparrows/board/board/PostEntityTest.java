package com.sparrows.board.board;

import com.sparrows.board.board.model.entity.CommentEntity;
import com.sparrows.board.board.model.entity.LikeEntity;
import com.sparrows.board.board.model.entity.PostEntity;
import com.sparrows.board.board.port.out.PostPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PostEntityTest {

    @Autowired
    private PostPort postPort;

    @Test
    public void post_댓글_좋아요_연관관계_저장_테스트() {
        // 1. 게시글 생성
        PostEntity post = PostEntity.builder()
                .title("테스트 제목")
                .content("내용입니다")
                .boardId(1)
                .userId(1001L)
                .url("test-post")
                .isHidden(false)
                .build();

        // 2. 댓글 생성
        CommentEntity comment = CommentEntity.builder()
                .userId(2002L)
                .post(post)
                .build();

        // 3. 좋아요 생성 (댓글, 게시글 각각)
        LikeEntity likeForPost = LikeEntity.builder()
                .userId(3003L)
                .post(post)
                .build();

        LikeEntity likeForComment = LikeEntity.builder()
                .userId(3004L)
                .comment(comment)
                .build();

        // 4. 연관 리스트에 추가 (양방향 관리)
        post.setComments(List.of(comment));
        post.setLikes(List.of(likeForPost));
        comment.setLikes(List.of(likeForComment));

        // 5. 저장
        postPort.save(post); // cascade 덕분에 comment/like도 같이 저장됨

        // 6. 검증
        PostEntity savedPost = postPort.findById(post.getId()).orElseThrow();
        assertEquals(1, savedPost.getComments().size());
        assertEquals(1, savedPost.getLikes().size());

        CommentEntity savedComment = savedPost.getComments().get(0);
        assertEquals(1, savedComment.getLikes().size());
    }
}
