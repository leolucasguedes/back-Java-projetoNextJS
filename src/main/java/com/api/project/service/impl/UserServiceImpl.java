package com.api.project.service.impl;

import org.springframework.stereotype.Service;
import com.api.project.model.dto.UserDTO;
import com.api.project.model.entity.User;
import com.api.project.repository.UserRepository;
import com.api.project.service.UserService;
import com.api.project.exceptions.NotFoundException;
import com.api.project.exceptions.UserAlreadyExistsException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO.getNome(),
                userDTO.getEmail(),
                userDTO.getSenha());

        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with the same email already exists");
        }

        return userRepository.save(newUser);
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
    public User signIn(UserDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }
}