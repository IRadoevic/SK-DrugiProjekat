package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import org.reservation.domain.RestoranLoyalty;

import javax.persistence.ManyToOne;
@Getter
@Setter
public class LoyaltyStatusKorisnikaDto {

    private  Integer userId;

    private Long restoranLoyaltyid;
}
