package org.reservation.Repository;

import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Restoran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DostupnostStolovaRepository extends JpaRepository<DostupnostStolova, Long> {
    Optional<DostupnostStolova> findById(Long id);
    // ovo je da dostupne stolove filtrira
    @Query("SELECT d FROM DostupnostStolova d " +
            "JOIN d.sto s " +
            "JOIN s.restoran r " +
            "WHERE (:tipKuhinje IS NULL OR r.tipKuhinje = :tipKuhinje) " +
            "AND (:lokacija IS NULL OR r.lokacija = :lokacija) " +
            "AND (:brojOsoba IS NULL OR s.brojMesta >= :brojOsoba) " +
            "AND (:datumVreme IS NULL OR d.dateTime = :datumVreme) " +
            "AND d.available = true")
    List<DostupnostStolova> findAvailableTerminiByFilters(String tipKuhinje, String lokacija, Integer brojOsoba, LocalDateTime datumVreme);

    @Query("SELECT d FROM DostupnostStolova d " +
            "WHERE (:idStola IS NULL OR d.sto.id = :idStola) " +
            "AND (:datumVreme IS NULL OR d.dateTime = :datumVreme) " +
            "AND d.available = true")
    Optional<DostupnostStolova> findAvailableByTableAndDate(Long idStola, LocalDateTime datumVreme);

    @Query("SELECT d FROM DostupnostStolova d " +
            "WHERE (:idStola IS NULL OR d.sto.id = :idStola) " +
            "AND (:datumVreme IS NULL OR d.dateTime = :datumVreme) "+
                    "AND (:userId IS NULL OR d.userId = :userId)")
    Optional<DostupnostStolova> findReservation(@Param("idStola") Long idStola,
                                                @Param("datumVreme") LocalDateTime datumVreme,
                                                @Param("userId") Long userId);

}
