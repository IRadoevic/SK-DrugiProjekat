package com.raf.mapper;


import com.raf.domain.User;
import com.raf.dto.UserCreateDto;
import com.raf.dto.ManagerCreateDto;
import com.raf.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserMapper {

    public UserMapper() {
    }

    // Metoda za konverziju User u Userdto
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    // Metoda za konverziju UserCreateDto u User
    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(userCreateDto.getRole());
        user.setDatumRodjenja((Date) userCreateDto.getDatumRodjenja());
        user.setBrojRezervacija(0);
        user.setDatumZaposljavanja(null);
        user.setNazivRestorana(null);
        return user;
    }

    public User managerCreateDtoToUser(ManagerCreateDto managerCreateDto) {
        User user = new User();
        user.setEmail(managerCreateDto.getEmail());
        user.setFirstName(managerCreateDto.getFirstName());
        user.setLastName(managerCreateDto.getLastName());
        user.setUsername(managerCreateDto.getUsername());
        user.setPassword(managerCreateDto.getPassword());
        user.setRole(managerCreateDto.getRole());
        user.setDatumRodjenja((Date) managerCreateDto.getDatumRodjenja());
        user.setDatumZaposljavanja((Date) managerCreateDto.getDatumZaposljavanja());
        user.setBrojRezervacija(null);
        user.setNazivRestorana(managerCreateDto.getNazivrestorana());
        return user;
    }
}
