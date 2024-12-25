package org.reservation.Repository;

import org.reservation.domain.LoyaltyStatusKorisnika;
import org.reservation.domain.RestoranLoyalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltySatatusKorinsikaRepository extends JpaRepository<LoyaltyStatusKorisnika, Long> {
}
