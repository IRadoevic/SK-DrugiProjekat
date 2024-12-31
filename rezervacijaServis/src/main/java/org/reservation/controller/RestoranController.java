package org.reservation.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.reservation.dto.RestoranDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.security.CheckSecurity;
import org.reservation.security.service.TokenService;
import org.reservation.service.RestoranService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/restoran")
public class RestoranController {
    private RestoranService restoranService;
    private  TokenService tokenService;

    public RestoranController(RestoranService restoranService, TokenService tokenService) {
        this.restoranService = restoranService;
        this.tokenService = tokenService;
    }

    @CheckSecurity(roles = {"MANAGER"})
    @PostMapping("/add")
    public ResponseEntity<RestoranDto> addRestoran(@RequestHeader("Authorization") String authorization,
                                                   @RequestBody RestoranDto restoranDto) {
        String token = authorization.split(" ")[1];
        Integer managerId = tokenService.getUserIdFromToken(token);
        RestoranDto createdRestoran = restoranService.addRestoran(restoranDto, managerId);
        return new ResponseEntity<>(createdRestoran, HttpStatus.CREATED);
    }

    @CheckSecurity(roles = {"MANAGER"})
    @PutMapping("/editRestoran")
    public ResponseEntity<Void> editRestoran(@RequestHeader("Authorization") String authorization,
                                             @RequestBody @Valid RestoranDto restoranDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);
        try {
            restoranService.editRestoran(userId, restoranDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ForbiddenException e) {
            System.err.println("ForbiddenException occurred: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            System.err.println("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
