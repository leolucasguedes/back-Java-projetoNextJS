package com.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.api.project.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
