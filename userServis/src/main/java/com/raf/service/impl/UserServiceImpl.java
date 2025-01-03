package com.raf.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.MessageBroker;
import com.raf.domain.User;
import com.raf.dto.*;
import com.raf.exeption.NotFoundException;
import com.raf.exeption.UserBannedException;
import com.raf.listener.MessageHelper;
import com.raf.mapper.UserMapper;
import com.raf.repository.UserRepository;
import com.raf.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private TokenService tokenService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;


    public UserServiceImpl(TokenService tokenService, UserRepository userRepository, UserMapper userMapper, JmsTemplate jmsTemplate, MessageHelper messageHelper) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
    }

    @Override
    public UserDto addUser(UserCreateDto userCreateDto) {
        Optional<User> ex = userRepository.findByUsername(userCreateDto.getUsername());
        if(ex.isPresent())
            throw new RuntimeException("Username already taken");
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        userRepository.save(user);
        // salji mejl
        // Kreiraj PorukaDto objekt
        PorukaDto porukaDto = new PorukaDto();
        porukaDto.setEmail(user.getEmail());
        porukaDto.setTipNotifikacije("Slanje aktivacionog imejla");
        // postovani %username aktiviali ste nalog
        porukaDto.setParametri(List.of(user.getUsername()));
        jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDto));

        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto addManager(ManagerCreateDto managerCreateDto) {
        Optional<User> ex = userRepository.findByUsername(managerCreateDto.getUsername());
        if(ex.isPresent())
            throw new RuntimeException("Username already taken");
        User user = userMapper.managerCreateDtoToUser(managerCreateDto);
        userRepository.save(user);
        // salji mejl
        PorukaDto porukaDto = new PorukaDto();
        porukaDto.setEmail(user.getEmail());
        porukaDto.setTipNotifikacije("Slanje aktivacionog imejla");
        // postovani %username aktiviali ste nalog
        porukaDto.setParametri(List.of(user.getUsername()));
        jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDto));
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
        if (user.getBanned()) {
            throw new UserBannedException("User is banned and can't login");
        }
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());
        claims.put("password",user.getPassword());
        claims.put("time", LocalDate.now());
        //Generate token
        System.out.println("Before generating token");
        String token = tokenService.generate(claims);
        System.out.println("Generated Token: " + token);
        return new TokenResponseDto(token);
    }
    @Transactional
    public boolean updateUser(Long id, UserUpdateDto userUpdateDto) {
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
            //jmsTemplate.convertAndSend("send_emails_queue", porukaDto);
            updated = true;
        }
        if (userUpdateDto.getDatumRodjenja() != null) {
            user.setDatumRodjenja(userUpdateDto.getDatumRodjenja());
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

    @Override
    public boolean verifyAcc(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

    /*@Override
    public Page<UserDto> findAll(Pageable pageable) {
        return null;
    }*/


}
