package org.reservation.dto;


public class LoyaltyStatusKorisnikaDto {

    private  Integer userId;

    private Long restoranLoyaltyid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getRestoranLoyaltyid() {
        return restoranLoyaltyid;
    }

    public void setRestoranLoyaltyid(Long restoranLoyaltyid) {
        this.restoranLoyaltyid = restoranLoyaltyid;
    }
}
