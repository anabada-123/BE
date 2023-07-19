package com.anabada.anabada.security.repository;

import com.anabada.anabada.security.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userid);

    Optional<User> findByNickname(String nickname);

}
