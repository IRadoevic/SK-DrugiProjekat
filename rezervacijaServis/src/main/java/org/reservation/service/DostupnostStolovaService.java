package org.reservation.service;

import org.reservation.domain.DostupnostStolova;
import org.reservation.dto.DostupnostDto;
import org.reservation.dto.FilterDostupnostiDto;
import org.reservation.dto.UpateDostupnostDto;

import java.time.LocalDateTime;
import java.util.List;

public interface DostupnostStolovaService {
    DostupnostStolova addDostupnost(DostupnostDto dostupnostDto, Integer maenadzerid);
    DostupnostStolova updateDostupnost(Long id, UpateDostupnostDto updateDostupnostDto, Integer userId);
    List<DostupnostStolova> findAvailableTerminiByFilters(FilterDostupnostiDto filterDostupnostiDto);

}
