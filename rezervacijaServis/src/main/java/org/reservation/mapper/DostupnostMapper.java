package org.reservation.mapper;

import org.reservation.Repository.StoRepository;
import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Sto;
import org.reservation.dto.DostupnostDto;
import org.reservation.dto.RezervacijaDto;
import org.reservation.exception.NotFoundException;

public class DostupnostMapper {
    private StoRepository stoRepository;
    public static DostupnostDto dostupnostToDostupnostDto(DostupnostStolova dostupnost) {
        DostupnostDto dostupnostDto = new DostupnostDto();
        dostupnostDto.setId(dostupnost.getId());
        dostupnostDto.setStoId(dostupnost.getSto().getId());
        dostupnostDto.setDateTime(dostupnost.getDateTime());
        dostupnostDto.setAvailable(dostupnost.isAvailable());
        return dostupnostDto;
    }

    public DostupnostStolova dostupnostDtoToDostupnost(DostupnostDto dostupnostDto) {
        DostupnostStolova dostupnost = new DostupnostStolova();
        Sto sto = stoRepository.findById(dostupnostDto.getStoId())
                .orElseThrow(() -> new NotFoundException("Sto sa ID-jem " + dostupnostDto.getStoId() + " nije pronađen."));
        dostupnost.setSto(sto);
        dostupnost.setDateTime(dostupnostDto.getDateTime());
        dostupnost.setAvailable(dostupnostDto.isAvailable());
        dostupnost.setUserId(null);
        return dostupnost;
    }


}
