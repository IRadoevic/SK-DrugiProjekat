package org.reservation.domain;


import javax.persistence.*;

@Entity
public class LoyaltyStatusKorisnika {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  Long userId;
    @ManyToOne
    private RestoranLoyalty restoranLoyalty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RestoranLoyalty getRestoranLoyalty() {
        return restoranLoyalty;
    }

    public void setRestoranLoyalty(RestoranLoyalty restoranLoyalty) {
        this.restoranLoyalty = restoranLoyalty;
    }
}
