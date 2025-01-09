package org.rag.repository;

import org.rag.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // izlista sve poruke i filtrira
    @Query("SELECT n FROM Notification n " +
            "JOIN n.notificationType nt " +
            "WHERE (:tip IS NULL OR nt.tip = :tip) " +
            "AND (:startDate IS NULL OR n.vremeSlanja >= :startDate) " +
            "AND (:endDate IS NULL OR n.vremeSlanja <= :endDate)" +
            "AND (:email IS NULL OR n.email = :email)")
    List<Notification> findAllEmailByFilter(@Param("tip")String tip,
                                            @Param("email")String email,
                                            @Param("startDate")LocalDateTime startDate,
                                            @Param("endDate")LocalDateTime endDate);

    // izlista samo od usera email i filtrira
    @Query("SELECT n FROM Notification n " +
            "JOIN n.notificationType nt " +
            "WHERE n.email = :email " +
            "AND (:tip IS NULL OR nt.tip = :tip) " +
            "AND (:startDate IS NULL OR n.vremeSlanja >= :startDate) " +
            "AND (:endDate IS NULL OR n.vremeSlanja <= :endDate)")
    List<Notification> findUserEmailByFilter(@Param("tip") String tip,
                                             @Param("email") String email,
                                             @Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);




}
