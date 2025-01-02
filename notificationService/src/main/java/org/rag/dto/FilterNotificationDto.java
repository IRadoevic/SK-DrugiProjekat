package org.rag.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class FilterNotificationDto {

 @NotBlank(message = "Tip cannot be blank")
 private String tip;

 @NotBlank(message = "Email cannot be blank")
 @Email(message = "Invalid email format")
 private String email;

 @NotNull(message = "Start date cannot be null")
 private LocalDateTime startDate;

 @NotNull(message = "End date cannot be null")
 private LocalDateTime endDate;
}
