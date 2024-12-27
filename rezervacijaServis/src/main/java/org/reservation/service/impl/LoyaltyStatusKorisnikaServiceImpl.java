package org.reservation.service.impl;

import org.reservation.domain.LoyaltyStatusKorisnika;
import org.reservation.domain.RestoranLoyalty;
import org.reservation.repository.LoyaltySatatusKorinsikaRepository;
import org.reservation.service.LoyaltyStatusKorisnikaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyStatusKorisnikaServiceImpl implements LoyaltyStatusKorisnikaService {
LoyaltySatatusKorinsikaRepository loyaltySatatusKorinsikaRepository;

    public LoyaltyStatusKorisnikaServiceImpl(LoyaltySatatusKorinsikaRepository loyaltySatatusKorinsikaRepository) {
        this.loyaltySatatusKorinsikaRepository = loyaltySatatusKorinsikaRepository;
    }

    @Override
    public void dodajPogodnostKorisniku(Long userid, RestoranLoyalty restoranLoyalty) {
        LoyaltyStatusKorisnika loyaltyStatusKorisnika = new LoyaltyStatusKorisnika();
        loyaltyStatusKorisnika.setUserId(userid);
        loyaltyStatusKorisnika.setRestoranLoyalty(restoranLoyalty);
        loyaltySatatusKorinsikaRepository.save(loyaltyStatusKorisnika);
    }

    @Override
    public List<RestoranLoyalty> svePogodnostiKojeJeKorisnikOstvario(Long userId) {
        return loyaltySatatusKorinsikaRepository.findRestoranLoyaltyByUserId(userId);
    }

}
