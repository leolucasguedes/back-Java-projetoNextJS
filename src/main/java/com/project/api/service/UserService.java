package com.project.api.service;

import com.project.api.controller.dto.UserDTO;
import com.project.api.model.User;

public interface UserService {
    User createUser(UserDTO userDTO);

    User getUserById(Long userId);

    User updateUserProfile(Long userId, UserDTO userDTO);
}
