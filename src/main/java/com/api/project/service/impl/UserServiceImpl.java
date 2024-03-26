package com.api.project.service.impl;

import org.springframework.stereotype.Service;
import com.api.project.model.dto.UserDTO;
import com.api.project.model.dto.LoginDTO;
import com.api.project.model.entity.User;
import com.api.project.repository.UserRepository;
import com.api.project.service.UserService;
import com.api.project.exceptions.NotFoundException;
import com.api.project.exceptions.UserAlreadyExistsException;
import com.api.project.exceptions.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        userDTO.setSenha(encoder.encode(userDTO.getSenha()));
        User newUser = new User(userDTO.getNome(),
                userDTO.getEmail(),
                userDTO.getSenha());

        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with the same email already exists");
        }

        return userRepository.save(newUser);
    }

    @Override
    public User signIn(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!encoder.matches(loginDTO.getSenha(), user.getSenha())) {
            throw new UnauthorizedException("Password does not match");
        }

        return user;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User updateUserProfile(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with the same email already exists");
        }

        existingUser.setNome(userDTO.getNome());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setSenha(userDTO.getSenha());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }
}