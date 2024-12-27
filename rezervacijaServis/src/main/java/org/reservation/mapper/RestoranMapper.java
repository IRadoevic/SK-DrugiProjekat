package org.reservation.mapper;

import org.reservation.domain.Restoran;
import org.reservation.dto.RestoranDto;
import org.springframework.stereotype.Component;

@Component
public class RestoranMapper {

    public RestoranDto restoranToRestoranDto(Restoran restoran)
    {
        RestoranDto restoranDto = new RestoranDto();
        restoranDto.setImeRestorana(restoran.getImeRestorana());
        restoranDto.setAdresa(restoran.getAdresa());
        restoranDto.setOpis(restoran.getOpis());
        restoranDto.setPocetakRadnogVremena(restoran.getPocetakRadnogVremena());
        restoranDto.setKrajRadnogVremena(restoran.getKrajRadnogVremena());
        restoranDto.setTipKuhinje(restoran.getTipKuhinje());
        restoranDto.setManagerId(restoran.getMenagerId());
        return restoranDto;
    }
    public Restoran restoranDtoToRestoran(RestoranDto restoranDto) {
        Restoran restoran = new Restoran();
        restoran.setImeRestorana(restoranDto.getImeRestorana());
        restoran.setAdresa(restoranDto.getAdresa());
        restoran.setOpis(restoranDto.getOpis());
        restoran.setPocetakRadnogVremena(restoranDto.getPocetakRadnogVremena());
        restoran.setKrajRadnogVremena(restoranDto.getKrajRadnogVremena());
        restoran.setTipKuhinje(restoranDto.getTipKuhinje());
        restoran.setMenagerId(restoranDto.getManagerId());
        return restoran;
    }
}
