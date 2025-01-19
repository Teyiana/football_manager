package org.zayarnyuk.football_manager.dto;

import lombok.Data;
import org.zayarnyuk.football_manager.entity.Role;

@Data
public class UserDTO {
        private Long id;
        private String username;
        private Role role;
        private String password;

}
