package org.zayarnyuk.football_manager.dto;

import lombok.Data;

@Data
public class ChangeRoleRequest {
    private Long userId;
    private String role;
}
