package org.zayarnyuk.football_manager.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zayarnyuk.football_manager.dto.UserDTO;
import org.zayarnyuk.football_manager.entity.User;
import org.zayarnyuk.football_manager.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public static final int USER_NAME_MIN_LENGTH = 5;
    public static final int USER_NAME_MAX_LENGTH = 50;
    public static final int USER_PASS_MIN_LENGTH = 8;
    public static final int USER_PASS_MAX_LENGTH = 100;

    private final UserRepository userRepository;

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::toDto);
    }

    public String register(UserDTO user) {
        validateUser(user);
        User entity = toEntity(user);
        LOGGER.info("User created: {}", user);
        userRepository.save(entity);
        return "User created";
    }

    private void validateUser(UserDTO user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + user.getUsername());
        }
        if (user.getUsername().length() < USER_NAME_MIN_LENGTH || user.getUsername().length() >= USER_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Username length must be between " + USER_NAME_MIN_LENGTH + " and " + USER_NAME_MAX_LENGTH);
        }
        if (user.getPassword().length() < USER_PASS_MIN_LENGTH || user.getPassword().length() >= USER_PASS_MAX_LENGTH) {
            throw new IllegalArgumentException("Password length must be between " + USER_PASS_MIN_LENGTH + " and " + USER_PASS_MAX_LENGTH);
        }
    }

    public Optional<UserDTO> findById(Long userId) {
        return userRepository.findById(userId).map(this::toDto);
    }

    public String update(UserDTO userDto) {
        UserDTO oldUser = findById(userDto.getId()).orElseThrow();
        if (userDto.getUsername() != null) {
            oldUser.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null) {
            oldUser.setPassword(userDto.getPassword());
        }
        if (userDto.getRole() != null) {
            oldUser.setRole(userDto.getRole());
        }
        userRepository.save(toEntity(oldUser));
        return "User updated";
    }

    private User toEntity(UserDTO user) {
        User entity = new User();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        return entity;
    }

    private UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }
}
