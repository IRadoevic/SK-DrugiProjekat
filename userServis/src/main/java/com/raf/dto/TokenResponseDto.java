package com.raf.dto;
/*
import lombok.Getter;
import lombok.Setter;

/*
@Getter
@Setter*/
public class TokenResponseDto {
    private String token;

    public TokenResponseDto() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public TokenResponseDto(String token) {

        this.token = token;
    }
}
