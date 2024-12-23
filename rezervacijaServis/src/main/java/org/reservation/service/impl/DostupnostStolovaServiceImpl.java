package org.reservation.service.impl;

import org.reservation.Repository.DostupnostStolovaRepository;
import org.reservation.Repository.RestoranRepository;
import org.reservation.Repository.StoRepository;
import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Restoran;
import org.reservation.domain.Sto;
import org.reservation.dto.DostupnostDto;
import org.reservation.dto.FilterDostupnostiDto;
import org.reservation.dto.UpateDostupnostDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.mapper.DostupnostMapper;
import org.reservation.service.DostupnostStolovaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DostupnostStolovaServiceImpl implements DostupnostStolovaService {
    private  StoRepository stoRepository;
    private  DostupnostStolovaRepository dostupnostRepository;
    private DostupnostMapper dostupnostMapper;
    private RestoranRepository restoranRepository;

    @Autowired
    public DostupnostStolovaServiceImpl(StoRepository stoRepository, DostupnostStolovaRepository dostupnostRepository) {
        this.stoRepository = stoRepository;
        this.dostupnostRepository = dostupnostRepository;
    }
    @Override
    public DostupnostStolova addDostupnost(DostupnostDto dostupnostDto, Integer menadzer) {
        Sto sto = stoRepository.findById(dostupnostDto.getStoId())
                .orElseThrow(() -> new NotFoundException("Sto sa ID-jem " + dostupnostDto.getStoId() + " nije pronađen."));

        Restoran restoran = restoranRepository.findById(sto.getRestoran().getId())
                .orElseThrow(() -> new NotFoundException("Restoran  nije pronađen."));

        if (!restoran.getMenagerId().equals(menadzer)) {
            throw new ForbiddenException("Nemate prava da dodate sto u ovaj restoran.");
        }

        DostupnostStolova dostupnostStolova = dostupnostMapper.dostupnostDtoToDostupnost(dostupnostDto);
        dostupnostStolova.setSto(sto);
        return dostupnostRepository.save(dostupnostStolova);
    }

    @Override
    public DostupnostStolova updateDostupnost(Long id, UpateDostupnostDto updateDostupnostDto,Integer userId ) {
        DostupnostStolova dostupnostStolova = dostupnostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dostupnost sa ID-jem " + id + " nije pronađena."));
        Sto sto = stoRepository.findById(dostupnostStolova.getSto().getId()) .orElseThrow(() -> new NotFoundException("Dostupnost sa ID-jem " + id + " nije pronađena."));

        Restoran restoran = restoranRepository.findById(sto.getRestoran().getId())
                .orElseThrow(() -> new NotFoundException("Restoran  nije pronađen."));

        if (!restoran.getMenagerId().equals(userId)) {
            throw new ForbiddenException("Nemate prava da dodate sto u ovaj restoran.");
        }

        if (updateDostupnostDto.getDateTime() != null) {
            dostupnostStolova.setDateTime(updateDostupnostDto.getDateTime());
        }
        dostupnostStolova.setAvailable(updateDostupnostDto.isAvailable());

        return dostupnostRepository.save(dostupnostStolova);
    }

    @Override
    public List<DostupnostStolova> findAvailableTerminiByFilters(FilterDostupnostiDto filterDostupnostiDto) {
        return dostupnostRepository.findAvailableTerminiByFilters(
                filterDostupnostiDto.getTipKuhinje(),
                filterDostupnostiDto.getLokacija(),
                filterDostupnostiDto.getBrojOsoba(),
                filterDostupnostiDto.getDatumVreme()
        );
    }
}
