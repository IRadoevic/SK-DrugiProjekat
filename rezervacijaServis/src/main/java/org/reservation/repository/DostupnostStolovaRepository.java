/*package org.reservation.repository;

import org.reservation.domain.DostupnostStolova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DostupnostStolovaRepository extends JpaRepository<DostupnostStolova, Long> {
    Optional<DostupnostStolova> findById(Long id);

    @Query("SELECT d FROM DostupnostStolova d " +
            "JOIN d.sto s " +
            "JOIN s.restoran r " +
            "WHERE (:tipKuhinje IS NULL OR r.tipKuhinje = :tipKuhinje) " +
            "AND (:lokacija IS NULL OR r.lokacija = :lokacija) " +
            "AND (:brojOsoba IS NULL OR s.brojMesta >= :brojOsoba) " +
            "AND (:datumVreme IS NULL OR d.dateTime = :datumVreme) " +
            "AND d.available = true")
    List<DostupnostStolova> findAvailableTerminiByFilters(
            @Param("tipKuhinje") String tipKuhinje,
            @Param("lokacija") String lokacija,
            @Param("brojOsoba") Integer brojOsoba,
            @Param("datumVreme") LocalDateTime datumVreme
    );

    @Query("SELECT d FROM DostupnostStolova d " +
            "WHERE (:idStola IS NULL OR d.sto.id = :idStola) " +
            "AND (:datumVreme IS NULL OR d.dateTime = :datumVreme) " +
            "AND d.available = true")
    Optional<DostupnostStolova> findAvailableByTableAndDate(
            @Param("idStola") Long idStola,
            @Param("datumVreme") LocalDateTime datumVreme
    );

    @Query("SELECT d FROM DostupnostStolova d " +
            "WHERE (:idStola IS NULL OR d.sto.id = :idStola) " +
            "AND (:datumVreme IS NULL OR d.dateTime = :datumVreme) " +
            "AND (:userId IS NULL OR d.userId = :userId)")
    Optional<DostupnostStolova> findReservation(
            @Param("idStola") Long idStola,
            @Param("datumVreme") LocalDateTime datumVreme,
            @Param("userId") Long userId
    );
}*/

package org.reservation.repository;

import org.reservation.domain.DostupnostStolova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DostupnostStolovaRepository extends JpaRepository<DostupnostStolova, Long> {

    //@Query("SELECT d FROM DostupnostStolova d WHERE d.id = :id")
    Optional<DostupnostStolova> findById(@Param("id") Long id);

    @Query("SELECT d FROM DostupnostStolova d JOIN d.sto s WHERE d.tipKuhinje = :tipKuhinje AND d.lokacija = :lokacija AND s.brojMesta = :brojOsoba AND d.datumVreme = :datumVreme")
    List<DostupnostStolova> findAvailableTerminiByFilters(
            @Param("tipKuhinje") String tipKuhinje,
            @Param("lokacija") String lokacija,
            @Param("brojOsoba") Integer brojOsoba,
            @Param("datumVreme") LocalDateTime datumVreme
    );

    @Query("SELECT d FROM DostupnostStolova d WHERE d.id = :id AND d.datumVreme = :datumVreme")
    Optional<DostupnostStolova> findAvailableByTableAndDate(
            @Param("id") Long id,
            @Param("datumVreme") LocalDateTime datumVreme
    );

    @Query("SELECT d FROM DostupnostStolova d WHERE d.id = :id AND d.datumVreme = :datumVreme AND d.userId = :userId")
    Optional<DostupnostStolova> findReservation(
            @Param("id") Long id,
            @Param("datumVreme") LocalDateTime datumVreme,
            @Param("userId") Long userId
    );
}


