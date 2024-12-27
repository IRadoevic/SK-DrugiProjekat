package org.reservation.repository;

import org.reservation.domain.LoyaltyStatusKorisnika;
import org.reservation.domain.RestoranLoyalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoyaltySatatusKorinsikaRepository extends JpaRepository<LoyaltyStatusKorisnika, Long> {

    // na osnovu user id vraca listu pogodnosti koje je korisnik ostvario
    @Query("SELECT l.restoranLoyalty FROM LoyaltyStatusKorisnika l WHERE l.userId = :userId")
    List<RestoranLoyalty> findRestoranLoyaltyByUserId(@Param("userId") Long userId);

}
