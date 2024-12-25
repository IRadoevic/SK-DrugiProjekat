package org.reservation.Repository;

import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.RestoranLoyalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestoranLoyaltyRepository extends JpaRepository<RestoranLoyalty, Long> {
    Optional<RestoranLoyalty> findById(Long restoranId);
}
