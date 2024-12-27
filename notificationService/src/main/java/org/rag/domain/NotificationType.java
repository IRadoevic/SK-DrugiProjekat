package org.rag.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tip;
    // !!!!!!! poruka gde treba parametri da se ubace ima %, npr Pozdrav %, bla bla
    private String tekst;


}
