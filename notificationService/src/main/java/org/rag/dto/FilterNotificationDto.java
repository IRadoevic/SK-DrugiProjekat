package org.rag.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterNotificationDto {
 private String tip;
 private String  email;
 private LocalDateTime startDate;
 private LocalDateTime endDate;

}
