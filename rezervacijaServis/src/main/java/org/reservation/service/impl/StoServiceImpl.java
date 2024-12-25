package org.reservation.service.impl;

import org.reservation.Repository.RestoranRepository;
import org.reservation.Repository.StoRepository;
import org.reservation.domain.Restoran;
import org.reservation.domain.Sto;
import org.reservation.dto.StoDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.mapper.StoMapper;
import org.reservation.service.StoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoServiceImpl implements StoService {
    private final StoRepository stoRepository;
    private final RestoranRepository restoranRepository;
    private final StoMapper stoMapper;


    public StoServiceImpl(StoRepository stoRepository, RestoranRepository restoranRepository, StoMapper stoMapper) {
        this.stoRepository = stoRepository;
        this.restoranRepository = restoranRepository;
        this.stoMapper = stoMapper;
    }

    @Override
    public Sto addSto(StoDto stoDto, Integer userId) {
        Restoran restoran = restoranRepository.findById(stoDto.getRestoranId())
                .orElseThrow(() -> new NotFoundException("Restoran sa ID-jem " + stoDto.getRestoranId() + " nije pronađen."));

        if (!restoran.getMenagerId().equals(userId)) {
            throw new ForbiddenException("Nemate prava da dodate sto u ovaj restoran.");
        }

        Sto sto = stoMapper.stoDtoUSto(stoDto);
        sto.setRestoran(restoran);

        return stoRepository.save(sto);
    }

    @Override
    public Sto updateSto(Long id, StoDto stoDto, Integer userId) {
        Sto sto = stoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sto sa ID-jem " + id + " nije pronađen."));

        if (!sto.getRestoran().getMenagerId().equals(userId)) {
            throw new ForbiddenException("Nemate prava da menjate ovaj sto.");
        }

        if (stoDto.getBrojMesta() != null) {
            sto.setBrojMesta(stoDto.getBrojMesta());
        }
        if (stoDto.getZona() != null) {
            sto.setZona(stoDto.getZona());
        }

        Restoran restoran = restoranRepository.findById(stoDto.getRestoranId())
                .orElseThrow(() -> new NotFoundException("Restoran sa ID-jem " + stoDto.getRestoranId() + " nije pronađen."));
        sto.setRestoran(restoran);

        return stoRepository.save(sto);
    }

}
