package com.raf.controller;

import com.raf.dto.*;
import com.raf.exeption.NotFoundException;
import com.raf.security.CheckSecurity;
import com.raf.service.impl.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    //private String orderDestination;

    public UserController(UserService userService /*String orderDestination*/) {
        this.userService = userService;
        //this.orderDestination = orderDestination;
    }

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    /*public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization,
                                                     Pageable pageable) {

        //return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }**/


    @PostMapping("/registerUser")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        try {
            userCreateDto.setRole("USER");
            UserDto userDto = userService.addUser(userCreateDto);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Greska: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Register manager")
    @PostMapping("/registerManager")  // Endpoint za registraciju menadžera
    public ResponseEntity<?> saveManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        try{
            managerCreateDto.setRole("MANAGER");
            return new ResponseEntity<>(userService.addManager(managerCreateDto), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Greska: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> proba() {
        return ResponseEntity.ok("UserController is working!");
    }


    @ApiOperation(value = "Ban user")
    @CheckSecurity(roles = {"ADMIN"})
    @PostMapping("/ban/{userId}")
    public ResponseEntity<String> banUser(@PathVariable Long userId,
                                          @RequestHeader("Authorization") String authorization) {
        try {
            userService.banUser(userId);
            return new ResponseEntity<>("User has been banned successfully.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    @ApiOperation(value = "Unban user")
    @CheckSecurity(roles = {"ADMIN"})
    @PostMapping("/unban/{userId}")
    public ResponseEntity<String> unbanUser(@PathVariable Long userId,
     @RequestHeader("Authorization") String authorization) {
        try {
            userService.unbanUser(userId);
            return new ResponseEntity<>("User has been unbanned successfully.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Login")
    //@CheckBan
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        try {
            return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Greska: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update User")
    //@CheckBan
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        boolean updated = userService.updateUser(id, userUpdateDto);
        if (updated) {
            return new ResponseEntity<>("User updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No fields were updated.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getBrRezervacija/{userId}")
    public ResponseEntity<UserBrRezervacijaDto> getUserReservationCount(@PathVariable Long userId) {
        UserBrRezervacijaDto userBrRezervacijaDto = userService.getUserReservationCount(userId);
        return new ResponseEntity<>(userBrRezervacijaDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Get user by ID")
    @GetMapping("getUser/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.vratiUsera(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Confirm registration")
    @GetMapping("/verify")
    public String verify(@RequestParam String token) {
        System.out.println("Verification token received: " + token);
        userService.verifyAcc(token);
        return token;
    }
}
