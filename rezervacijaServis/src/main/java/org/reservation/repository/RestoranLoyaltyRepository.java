package org.reservation.repository;

import org.reservation.domain.Restoran;
import org.reservation.domain.RestoranLoyalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestoranLoyaltyRepository extends JpaRepository<RestoranLoyalty, Long> {
    List<RestoranLoyalty> findByRestoran(Restoran restoran);
}
