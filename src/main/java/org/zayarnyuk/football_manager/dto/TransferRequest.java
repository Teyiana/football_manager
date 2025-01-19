package org.zayarnyuk.football_manager.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private long playerId;
    private long teamId;
}
