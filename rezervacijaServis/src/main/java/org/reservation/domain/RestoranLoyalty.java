package org.reservation.domain;


import javax.persistence.*;

@Entity
public class RestoranLoyalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Restoran restoran;

    private  Integer uslov;
    private String nagrada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

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
}
