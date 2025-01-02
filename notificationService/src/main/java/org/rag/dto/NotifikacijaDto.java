package org.rag.dto;

import lombok.Getter;
import lombok.Setter;
import org.rag.domain.NotificationType;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class NotifikacijaDto {
    NotificationType notificationType;
    private String email;
    private String tekst;
    private LocalDateTime vremeSlanja;
    private String username;
}
