package com.sparrows.board.board.config;

import com.sparrows.board.board.model.entity.BoardEntity;
import com.sparrows.board.board.port.out.BoardPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"local", "dev"})
public class BoardSeedInitializer {

    private final BoardPort boardPort;

    @PostConstruct
    public void init() {
        long count = boardPort.count();
        if (count > 0) return;

        List<BoardEntity> seedData = List.of(
                create("학교", "전체 학교 게시판", true, "전체 학교 게시판"),
                create("중고거래", "중고거래 게시판", true, "중고거래 게시판"),
                create("학원 리뷰", "학원 리뷰 게시판", true, "학원 리뷰 게시판")
        );

        boardPort.saveAll(seedData);
        log.info("[시드 데이터] Board 데이터 초기화 완료");
    }

    private BoardEntity create(String name, String notice, boolean isPublic, String description) {
        return BoardEntity.builder()
                .name(name)
                .notice(notice)
                .isPublic(isPublic)
                .description(description)
                .entercodeChangedAt(LocalDateTime.now())
                .madeBy(0L)
                .schoolId(0)
                .build();
    }
}
