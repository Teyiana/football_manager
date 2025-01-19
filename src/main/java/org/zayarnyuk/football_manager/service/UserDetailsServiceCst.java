package org.zayarnyuk.football_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zayarnyuk.football_manager.dto.UserDTO;
import org.zayarnyuk.football_manager.entity.Role;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceCst implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDTO user = userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            String[] roles = getRoles(user);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("User not found with username: " + username);
        }

    }

    private String[] getRoles(UserDTO user) {
        Role role = user.getRole();
        List<String> roles = new ArrayList<>();
        for (Role r : Role.values()) {
            if (r.ordinal() <= role.ordinal()) {
                roles.add(r.name());
            }
        }
        return roles.toArray(new String[0]);
    }
}

