package com.project.api.service.impl;

import com.project.api.controller.dto.UserDTO;
import com.project.api.model.User;
import com.project.api.repository.UserRepository;
import com.project.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setNome(userDTO.getNome());
        newUser.setEmail(userDTO.getEmail());
        newUser.setSenha(userDTO.getSenha());

        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User updateUserProfile(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser != null) {
            existingUser.setNome(userDTO.getNome());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setSenha(userDTO.getSenha());

            return userRepository.save(existingUser);
        }

        return null; // ou lançar uma exceção indicando que o usuário não foi encontrado
    }
}
