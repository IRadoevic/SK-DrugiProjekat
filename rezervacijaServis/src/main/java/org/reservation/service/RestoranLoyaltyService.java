package org.reservation.service;

import org.reservation.domain.LoyaltyStatusKorisnika;
import org.reservation.domain.RestoranLoyalty;
import org.reservation.dto.LoyaltyStatusKorisnikaDto;
import org.reservation.dto.RestoranLoyaltyDto;

public interface RestoranLoyaltyService {
    RestoranLoyalty dodajPogodnostiZaRestoran(RestoranLoyaltyDto restoranLoyaltyDto, Integer idMenadzera);

}
