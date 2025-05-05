package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.UpdateUserRequest;
import com.openclassrooms.mddapi.dto.response.UserResponseDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final IUserService userService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser() {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();



            UserResponseDTO dto = userMapper.toDto(userService.getUserByMail(username));
            return ResponseEntity.ok(dto);

    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UpdateUserRequest request) {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();

User user = userService.getUserByMailorUserName(   username);
            User updated = userService.updateUser(user.getId(), request);
            UserResponseDTO dto = userMapper.toDto(updated);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);

    }}
