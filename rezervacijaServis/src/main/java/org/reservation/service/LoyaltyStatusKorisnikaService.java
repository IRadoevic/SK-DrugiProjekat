package org.reservation.service;

import org.reservation.domain.LoyaltyStatusKorisnika;
import org.reservation.domain.RestoranLoyalty;

import java.util.List;

public interface LoyaltyStatusKorisnikaService {
    void dodajPogodnostKorisniku(Long userid, RestoranLoyalty restoranLoyalty);
    List<RestoranLoyalty> svePogodnostiKojeJeKorisnikOstvario(Long userId);

}
