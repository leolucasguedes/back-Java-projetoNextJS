package com.api.project.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.api.project.model.entity.User;
import com.api.project.security.UserDataAuth;
import com.api.project.repository.UserRepository;
import java.util.Optional;

@Component
public class AuthorizationServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuário [" + email + "] não encontrado");
        }

        User user = findUser.get();

        return new UserDataAuth(user);
    }

}
