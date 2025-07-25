package org.rag.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PorukaDto {
    @NotBlank
    private String tipNotifikacije;
    private List<String> parametri;
    @Email
    private String email;
}
