package com.sparrows.board.board.model.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardWithdrawRequestDto {
    private Integer boardId;
    private Long transferToUserId;
}