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

    @Query("SELECT d FROM DostupnostStolova d WHERE d.userId = :id")
    List<DostupnostStolova> findByUserId(@Param("id") Long id);

    @Query("SELECT d FROM DostupnostStolova d JOIN FETCH d.sto s JOIN FETCH s.restoran r WHERE r.menagerId = :id AND d.dostupnostStolova=false")
    List<DostupnostStolova> findByManagerId(@Param("id") int id);


    @Query("SELECT d FROM DostupnostStolova d JOIN d.sto s JOIN s.restoran r " +
            "WHERE (:tipKuhinje IS NULL OR d.tipKuhinje = :tipKuhinje) " +
            "AND (:lokacija IS NULL OR d.lokacija = :lokacija) " +
            "AND (:brojOsoba IS NULL OR s.brojMesta >= :brojOsoba) " +
            "AND (:datumVreme IS NULL OR d.datumVreme = :datumVreme) " +
            "AND d.dostupnostStolova = true")
    List<DostupnostStolova> findAvailableTerminiByFilters(
            @Param("tipKuhinje") String tipKuhinje,
            @Param("lokacija") String lokacija,
            @Param("brojOsoba") Integer brojOsoba,
            @Param("datumVreme") LocalDateTime datumVreme
    );

    @Query("SELECT d FROM DostupnostStolova d " +
            "JOIN d.sto s " +
            "WHERE s.id = :id " +
            "AND d.datumVreme = :datumVreme " +
            "AND d.dostupnostStolova = true")
    Optional<DostupnostStolova> findAvailableByTableAndDate(
            @Param("id") Long id,
            @Param("datumVreme") LocalDateTime datumVreme
    );

    @Query("SELECT d FROM DostupnostStolova d JOIN d.sto s " +
            "WHERE s.id = :id AND d.datumVreme = :datumVreme AND d.userId = :userId")
    Optional<DostupnostStolova> findReservation(
            @Param("id") Long id,
            @Param("datumVreme") LocalDateTime datumVreme,
            @Param("userId") Long userId
    );

    @Query("SELECT d FROM DostupnostStolova d JOIN d.sto s " +
            "WHERE s.id = :id AND d.datumVreme = :datumVreme")
    Optional<DostupnostStolova> findReservationm(
            @Param("id") Long id,
            @Param("datumVreme") LocalDateTime datumVreme
    );
}


