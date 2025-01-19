package org.zayarnyuk.football_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zayarnyuk.football_manager.dto.ChangeRoleRequest;
import org.zayarnyuk.football_manager.util.JwtUtil;
import org.zayarnyuk.football_manager.dto.UserRequest;
import org.zayarnyuk.football_manager.dto.UserDTO;
import org.zayarnyuk.football_manager.entity.Role;
import org.zayarnyuk.football_manager.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody UserRequest user) {
        UserDTO userDto = new UserDTO();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(passwordEncoder.encode(user.getPassword()));
        userDto.setRole(Role.USER);
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/changeRole")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> changeRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        try{
            UserDTO userDto = userService.findById(changeRoleRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userDto.setRole(Role.valueOf(changeRoleRequest.getRole()));
            return ResponseEntity.ok(userService.update(userDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
