package com.raf.service.impl;

import com.raf.MessageBroker;
import com.raf.domain.User;
import com.raf.dto.*;
import com.raf.exeption.NotFoundException;
import com.raf.mapper.UserMapper;
import com.raf.repository.UserRepository;
import com.raf.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private TokenService tokenService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private JmsTemplate jmsTemplate; // Dodaj JmsTemplate za slanje poruka


    public UserServiceImpl(TokenService tokenService, UserRepository userRepository, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto addUser(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        userRepository.save(user);
        // salji mejl
        // Kreiraj PorukaDto objekt
        PorukaDto porukaDto = new PorukaDto();
        porukaDto.setEmail(user.getEmail());
        porukaDto.setTipNotifikacije("Slanje aktivacionog imejla");
        // postovani %username aktiviali ste nalog
        porukaDto.setParametri(List.of(user.getUsername()));
        jmsTemplate.convertAndSend("send_emails_queue", porukaDto);

        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto addManager(ManagerCreateDto managerCreateDto) {
        User user = userMapper.managerCreateDtoToUser(managerCreateDto);
        userRepository.save(user);
        // salji mejl
        PorukaDto porukaDto = new PorukaDto();
        porukaDto.setEmail(user.getEmail());
        porukaDto.setTipNotifikacije("Slanje aktivacionog imejla");
        // postovani %username aktiviali ste nalog
        porukaDto.setParametri(List.of(user.getUsername()));
        jmsTemplate.convertAndSend("send_emails_queue", porukaDto);
        return userMapper.userToUserDto(user);
    }
    @Transactional
    public void banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(true);userRepository.save(user);
    }

    @Transactional
    public void unbanUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(false);
        userRepository.save(user);
    }
    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
    @Transactional
    public boolean updateUser(Long id, UserUpdateDto userUpdateDto) {
        // Pronadji korisnika po id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        boolean updated = false;
        // Azuriraj samo polja koja nisu null
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
            updated = true;
        }
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
            updated = true;
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
            updated = true;
        }
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
            updated = true;
        }
        if (userUpdateDto.getPassword() != null && userUpdateDto.getPassword().equals("")) {
            String stara = user.getPassword();
            user.setPassword(userUpdateDto.getPassword());
            // posalji da si promenio lozinku
            PorukaDto porukaDto = new PorukaDto();
            porukaDto.setEmail(user.getEmail());
            porukaDto.setTipNotifikacije("Slanje aktivacionog imejla");
            // %username promenili ste lozinku sa %stara na % nova
            porukaDto.setParametri(List.of(user.getUsername(), stara,userUpdateDto.getPassword() ));
            jmsTemplate.convertAndSend("send_emails_queue", porukaDto);
            updated = true;
        }
        if (userUpdateDto.getDatumRodjenja() != null) {
            user.setDatumRodjenja((Date) userUpdateDto.getDatumRodjenja());
            updated = true;
        }
        userRepository.save(user);
        return  updated;
    }

    @Override
    public void incrementReservationCount(IncrementReservationCountDto incrementReservationCountDto) {
        userRepository.findById(incrementReservationCountDto.getUserId())
                .ifPresent(user1 -> {
                    user1.setBrojRezervacija(user1.getBrojRezervacija() + 1);
                    userRepository.save(user1);});
    }

    @Override
    public void decrementReservationCount(DecrementReservationCountDto decrementReservationCountDto) {
        userRepository.findById(decrementReservationCountDto.getUserId())
                .ifPresent(user1 -> {
                    user1.setBrojRezervacija(user1.getBrojRezervacija() - 1);
                    userRepository.save(user1);});
    }

    public UserBrRezervacijaDto getUserReservationCount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Integer reservationCount = user.getBrojRezervacija();

        UserBrRezervacijaDto userBrRezervacijaDto = new UserBrRezervacijaDto();
        userBrRezervacijaDto.setId(user.getId());
        userBrRezervacijaDto.setRezervacija(reservationCount);
        return userBrRezervacijaDto;
    }

    @Override
    public UserDto vratiUsera(Long id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }


}
