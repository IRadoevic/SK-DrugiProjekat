package org.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class LoyaltyStatusKorisnika {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  Long userId;
    @ManyToOne
    private RestoranLoyalty restoranLoyalty;


}
