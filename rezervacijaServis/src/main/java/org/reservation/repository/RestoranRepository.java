package org.reservation.repository;

import org.reservation.domain.Restoran;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestoranRepository extends JpaRepository<Restoran, Long> {
    Optional<Restoran> findById(Long id);

}
