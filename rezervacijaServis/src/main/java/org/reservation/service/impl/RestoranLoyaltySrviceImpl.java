package org.reservation.service.impl;

import org.reservation.repository.RestoranLoyaltyRepository;
import org.reservation.repository.RestoranRepository;
import org.reservation.domain.Restoran;
import org.reservation.domain.RestoranLoyalty;
import org.reservation.dto.RestoranLoyaltyDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.service.RestoranLoyaltyService;
import org.springframework.stereotype.Service;

@Service
public class RestoranLoyaltySrviceImpl implements RestoranLoyaltyService {
    RestoranRepository restoranRepository;
    RestoranLoyaltyRepository restoranLoyaltyRepository;

    public RestoranLoyaltySrviceImpl(RestoranRepository restoranRepository, RestoranLoyaltyRepository restoranLoyaltyRepository) {
        this.restoranRepository = restoranRepository;
        this.restoranLoyaltyRepository = restoranLoyaltyRepository;
    }

    @Override
    public RestoranLoyalty dodajPogodnostiZaRestoran(RestoranLoyaltyDto restoranLoyaltyDto, Integer idMenadzera, boolean isAdmin) {
        Restoran restoran = restoranRepository.findById(restoranLoyaltyDto.getIdRestorana())
                .orElseThrow(() -> new NotFoundException("Restoran nije pronađen."));

        // Proveravamo da li je korisnik menadzer ovog restorana
        if (!isAdmin && !restoran.getMenagerId().equals(idMenadzera)) {
            throw new ForbiddenException("Korisnik nije menadžer ovog restorana niti admin.");
        }
        RestoranLoyalty restoranLoyalty = new RestoranLoyalty();
        restoranLoyalty.setUslov(restoranLoyaltyDto.getUslov());
        restoranLoyalty.setNagrada(restoranLoyaltyDto.getNagrada());
        restoranLoyalty.setRestoran(restoran);

        return restoranLoyaltyRepository.save(restoranLoyalty);
    }

}
