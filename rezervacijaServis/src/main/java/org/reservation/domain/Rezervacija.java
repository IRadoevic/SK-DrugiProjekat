package org.reservation.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Sto sto;
    private  Integer userId;
    private LocalDateTime dateTime; // proverimo dostupnost za ovaj sto
}
