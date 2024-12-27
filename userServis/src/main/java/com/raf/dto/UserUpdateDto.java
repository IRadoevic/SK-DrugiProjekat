package com.raf.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserUpdateDto {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password; 
    private Data datumRodjenja;
}
