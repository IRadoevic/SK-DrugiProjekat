package org.reservation.Repository;

import org.reservation.domain.Restoran;
import org.reservation.domain.Sto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoRepository extends JpaRepository<Sto, Long> {
}
