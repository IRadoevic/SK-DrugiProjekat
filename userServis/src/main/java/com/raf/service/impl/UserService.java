package com.raf.service.impl;

import com.raf.dto.*;

public interface UserService {
    UserDto addUser(UserCreateDto userCreateDto);
    UserDto addManager(ManagerCreateDto managerCreateDto);
    void banUser(Long userId);
    void unbanUser(Long userId);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    boolean updateUser(Long id, UserUpdateDto userUpdateDto);
    void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto);
    void decrementReservationCount(DecrementReservationCountDto decrementReservationCountDto );
    UserBrRezervacijaDto getUserReservationCount(Long userId);
    UserDto vratiUsera(Long id);

}
