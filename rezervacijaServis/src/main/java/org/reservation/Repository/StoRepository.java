package org.reservation.Repository;

import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Restoran;
import org.reservation.domain.Sto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoRepository extends JpaRepository<Sto, Long> {
    Optional<Sto> findById(Long id);
}
