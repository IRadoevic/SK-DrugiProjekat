package org.reservation.mapper;

import org.reservation.repository.StoRepository;
import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Sto;
import org.reservation.dto.DostupnostDto;
import org.reservation.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DostupnostMapper {
    private StoRepository stoRepository;

    public DostupnostMapper(StoRepository stoRepository){
        this.stoRepository = stoRepository;
    }

    public static DostupnostDto dostupnostToDostupnostDto(DostupnostStolova dostupnost) {
        DostupnostDto dostupnostDto = new DostupnostDto();
        dostupnostDto.setStoId(dostupnost.getSto().getId());
        dostupnostDto.setDateTime(dostupnost.getDatumVreme());
        dostupnostDto.setAvailable(dostupnost.isDostupnostStolova());
        return dostupnostDto;
    }

    public DostupnostStolova dostupnostDtoToDostupnost(DostupnostDto dostupnostDto) {
        DostupnostStolova dostupnost = new DostupnostStolova();
        Sto sto = stoRepository.findById(dostupnostDto.getStoId())
                .orElseThrow(() -> new NotFoundException("Sto sa ID-jem " + dostupnostDto.getStoId() + " nije pronađen."));
        dostupnost.setSto(sto);
        dostupnost.setDatumVreme(dostupnostDto.getDateTime());
        dostupnost.setDostupnostStolova(dostupnostDto.isAvailable());
        dostupnost.setUserId(null);
        return dostupnost;
    }


}
