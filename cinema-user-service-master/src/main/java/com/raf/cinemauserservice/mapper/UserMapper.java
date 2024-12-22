package com.raf.cinemauserservice.mapper;

import com.raf.cinemauserservice.domain.Address;
import com.raf.cinemauserservice.domain.User;
import com.raf.cinemauserservice.dto.UserCreateDto;
import com.raf.cinemauserservice.dto.UserDto;
import com.raf.cinemauserservice.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName("ROLE_USER").get());
        user.setNumberOfReservations(0);
        //Set address
        Address address = new Address();
        address.setCountry(userCreateDto.getAddress().getCountry());
        address.setCity(userCreateDto.getAddress().getCity());
        address.setPostcode(userCreateDto.getAddress().getPostcode());
        address.setStreet(userCreateDto.getAddress().getStreet());
        address.setNumber(userCreateDto.getAddress().getNumber());
        address.setApartmentNumber(userCreateDto.getAddress().getApartmentNumber());
        user.setAddress(address);
        return user;
    }
}
