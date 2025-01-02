package org.rag.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    NotificationType notificationType;
    //mzd ovo umesto teksta, pa da se samo ubace gde treba te vr
    //private List<String> args;
    private String email;
    private String tekst;
    private LocalDateTime vremeSlanja;
}
