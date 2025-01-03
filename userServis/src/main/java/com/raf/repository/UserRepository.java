package com.raf.repository;

import com.raf.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}
