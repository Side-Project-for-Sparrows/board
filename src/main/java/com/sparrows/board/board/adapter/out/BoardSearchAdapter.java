package com.sparrows.board.board.adapter.out;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.board.board.feignclient.BoardIndexClient;
import com.sparrows.board.board.model.dto.internal.BoardSearchRequest;
import com.sparrows.board.board.model.dto.internal.BoardSearchResponse;
import com.sparrows.board.board.port.out.BoardSearchPort;
import com.sparrows.board.kafka.outbox.OutboxEventRepository;
import com.sparrows.board.kafka.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BoardSearchAdapter implements BoardSearchPort {

    private final BoardIndexClient boardIndexClient;
    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    @Override
    public BoardSearchResponse search(BoardSearchRequest request) {
        return boardIndexClient.searchBoard(request);
    }
}
