package com.raf.cinemauserservice.repository;

import com.raf.cinemauserservice.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}
