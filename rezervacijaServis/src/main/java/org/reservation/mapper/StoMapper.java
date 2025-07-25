package org.reservation.mapper;

import org.reservation.repository.RestoranRepository;
import org.reservation.domain.Restoran;
import org.reservation.domain.Sto;
import org.reservation.dto.StoDto;
import org.reservation.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class StoMapper {
    private  RestoranRepository restoranRepository;

    public StoMapper(RestoranRepository restoranRepository) {
        this.restoranRepository = restoranRepository;
    }


    public static StoDto stoUStoDto(Sto sto) {
        if (sto == null) {
            throw new RuntimeException("Trying to map sto that is null");
            //return null;
        }

        StoDto stoDto = new StoDto();
        stoDto.setRestoranId(sto.getRestoran().getId());
        stoDto.setBrojMesta(sto.getBrojMesta());
        stoDto.setZona(sto.getZona());

        return stoDto;
    }

    public Sto stoDtoUSto(StoDto stoDto) {
        if (stoDto == null) {
            throw new RuntimeException("Trying to map to sto, but sto is null");
            //return null;
        }
        Sto sto = new Sto();
        Restoran restoran = restoranRepository.findById(stoDto.getRestoranId())
                .orElseThrow(() -> new NotFoundException("Restoran sa ID-jem " + stoDto.getRestoranId() + " nije pronađen."));
        sto.setRestoran(restoran);
        sto.setBrojMesta(stoDto.getBrojMesta());
        sto.setZona(stoDto.getZona());

        return sto;
    }
}
