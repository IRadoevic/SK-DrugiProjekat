package org.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IdClass(StoId.class)  // Kombinovano primerni kljuc
public class Sto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    private Integer brojMesta;
    private String zona;

}