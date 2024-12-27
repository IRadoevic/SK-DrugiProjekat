package org.reservation.service;

import org.reservation.dto.RestoranDto;

public interface RestoranService {
    RestoranDto addRestoran(RestoranDto restoranDto, Integer maganerid);
    void editRestoran(Integer menadyerId,RestoranDto restoranDto);
}
