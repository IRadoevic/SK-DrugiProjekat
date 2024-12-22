package com.raf.cinemauserservice.service;


import com.raf.cinemauserservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    DiscountDto findDiscount(Long id);

    UserDto add(UserCreateDto userCreateDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto);
}
