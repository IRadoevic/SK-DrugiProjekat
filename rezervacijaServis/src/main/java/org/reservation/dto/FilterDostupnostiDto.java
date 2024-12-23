package org.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterDostupnostiDto {
    private String tipKuhinje;
    private String lokacija;
    private Integer brojOsoba;
    private LocalDateTime datumVreme;
}
