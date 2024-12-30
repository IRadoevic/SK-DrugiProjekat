package com.raf.dto;
/*
import lombok.Getter;
import lombok.Setter;

/*
@Getter
@Setter*/
public class NotificationRequestDto {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getNotificationType() {
        return notificationType;
    }

    private String email;
    private String notificationType;
}
