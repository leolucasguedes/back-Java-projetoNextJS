package com.api.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.api.project.exceptions.NotFoundException;
import com.api.project.exceptions.UserAlreadyExistsException;
import com.api.project.model.dto.UserDTO;
import com.api.project.model.entity.User;
import com.api.project.service.UserService;
import com.api.project.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody UserDTO userDTO) {
        try {
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.signIn(userDTO);
            String token = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateProfile(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUserProfile(userId, userDTO);
            return ResponseEntity.ok().body(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/profile/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}