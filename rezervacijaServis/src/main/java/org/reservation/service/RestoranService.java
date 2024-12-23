package org.reservation.service;

import org.reservation.dto.RestoranDto;

public interface RestoranService {
    RestoranDto addRestoran(RestoranDto restoranDto);
    void editRestoran(Integer menadyerId,RestoranDto restoranDto);
}
