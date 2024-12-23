package org.reservation.service.impl;

import org.reservation.Repository.RestoranRepository;
import org.reservation.domain.Restoran;
import org.reservation.dto.RestoranDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.mapper.RestoranMapper;
import org.reservation.security.service.TokenService;
import org.reservation.service.RestoranService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestoranImpl implements RestoranService {
    private TokenService tokenService;
    private RestoranRepository restoranRepository;
    private RestoranMapper restoranMapper;

    public RestoranImpl(TokenService tokenService, RestoranRepository restoranRepository, RestoranMapper restoranMapper) {
        this.tokenService = tokenService;
        this.restoranRepository = restoranRepository;
        this.restoranMapper = restoranMapper;
    }

    @Override
    public RestoranDto addRestoran(RestoranDto restoranDto) {
        Restoran restoran = restoranMapper.restoranDtoToRestoran(restoranDto);
        restoranRepository.save(restoran);
        return restoranMapper.restoranToRestoranDto(restoran);
    }

    @Override
    public void editRestoran(Integer userId, RestoranDto restoranDto) {
        if (restoranDto.getId() == null) {
            throw new IllegalArgumentException("ID restorana ne sme biti null.");
        }

        Restoran restoran = restoranRepository.findById(restoranDto.getId())
                .orElseThrow(() -> new NotFoundException("Restoran sa ID-jem " + restoranDto.getId() + " nije pronađen."));

        if (!restoran.getMenagerId().equals(userId)) {
            throw new ForbiddenException("Nemate prava da menjate ovaj restoran.");
        }

        if (restoranDto.getImeRestorana() != null) {
            restoran.setImeRestorana(restoranDto.getImeRestorana());
        }
        if (restoranDto.getAdresa() != null) {
            restoran.setAdresa(restoranDto.getAdresa());
        }
        if (restoranDto.getOpis() != null) {
            restoran.setOpis(restoranDto.getOpis());
        }

        if (restoranDto.getPocetakRadnogVremena() != null) {
            restoran.setPocetakRadnogVremena(restoranDto.getPocetakRadnogVremena());
        }
        if (restoranDto.getKrajRadnogVremena() != null) {
            restoran.setKrajRadnogVremena(restoranDto.getKrajRadnogVremena());
        }
        if (restoranDto.getTipKuhinje() != null) {
            restoran.setTipKuhinje(restoranDto.getTipKuhinje());
        }

        restoranRepository.save(restoran);
    }
}
