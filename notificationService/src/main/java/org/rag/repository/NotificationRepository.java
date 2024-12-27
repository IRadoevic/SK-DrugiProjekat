package org.rag.repository;

import org.rag.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // izlista sve poruke i filtrira
    @Query("SELECT n FROM Notification n " +
            "JOIN n.notificationType nt " +
            "WHERE (:tip IS NULL OR nt.tip = :tip) " +
            "AND (:startDate IS NULL OR n.vremeSlanja >= :startDate) " +
            "AND (:endDate IS NULL OR n.vremeSlanja <= :endDate)")
    List<Notification> findAllEmailByFilter(String tipNotifikacije, LocalDateTime startDate, LocalDateTime endDate);

    // izlista samo od usera email i filtirria
    @Query("SELECT n FROM Notification n " +
            "JOIN n.notificationType nt " +
            "WHERE (:tip IS NULL OR nt.tip = :tip) " +
            "AND (:email IS NULL OR n.email = :email) " +
            "AND (:startDate IS NULL OR n.vremeSlanja >= :startDate) " +
            "AND (:endDate IS NULL OR n.vremeSlanja <= :endDate)")
    List<Notification> findUserEmailByFilter(String tipNotifikacije, String email, LocalDateTime startDate, LocalDateTime endDate);


}
