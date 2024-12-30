package org.reservation.dto;

public class RestoranLoyaltyDto {

    private  Integer uslov;
    private String nagrada;
    private  Long idRestorana;

    public Integer getUslov() {
        return uslov;
    }

    public void setUslov(Integer uslov) {
        this.uslov = uslov;
    }

    public String getNagrada() {
        return nagrada;
    }

    public void setNagrada(String nagrada) {
        this.nagrada = nagrada;
    }

    public Long getIdRestorana() {
        return idRestorana;
    }

    public void setIdRestorana(Long idRestorana) {
        this.idRestorana = idRestorana;
    }
}
