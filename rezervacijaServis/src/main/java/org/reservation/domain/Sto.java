package org.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity

public class Sto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restoran_id", referencedColumnName = "id", nullable = false)
    private Restoran restoran;

    private Integer brojMesta;
    private String zona;

}