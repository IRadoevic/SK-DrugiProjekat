package org.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class StoId implements Serializable {
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "id")
    private Long id;

}
