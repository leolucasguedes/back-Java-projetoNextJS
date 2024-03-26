package com.api.project.service;

import com.api.project.model.dto.LoginDTO;
import com.api.project.model.dto.UserDTO;
import com.api.project.model.entity.User;

public interface UserService {
    User createUser(UserDTO userDTO);

    User signIn(LoginDTO loginDTO);

    User getUserById(Long userId);

    User updateUserProfile(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);
}