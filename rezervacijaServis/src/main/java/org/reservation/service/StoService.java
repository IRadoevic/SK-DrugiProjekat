package org.reservation.service;

import org.reservation.domain.Sto;
import org.reservation.dto.StoDto;

public interface StoService {
    Sto addSto(StoDto stoDto, Integer userId);
    Sto updateSto(Long id, StoDto stoDto, Integer userId);
}
