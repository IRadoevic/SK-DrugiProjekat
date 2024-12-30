package org.reservation.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.reservation.dto.RestoranLoyaltyDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.security.service.TokenService;
import org.reservation.service.RestoranLoyaltyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.reservation.security.CheckSecurity;

import javax.validation.Valid;

@RestController
@RequestMapping("/restoranLoyalty")
public class RestoranLoyaltyController {

    private final RestoranLoyaltyService restoranLoyaltyService;
    private final TokenService tokenService;

    public RestoranLoyaltyController(RestoranLoyaltyService restoranLoyaltyService, TokenService tokenService) {
        this.restoranLoyaltyService = restoranLoyaltyService;
        this.tokenService = tokenService;
    }


    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    @PostMapping("/add")
    public ResponseEntity<String> addLoyaltyForRestoran(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody @Valid RestoranLoyaltyDto restoranLoyaltyDto) {
        try {
            String token = authorization.split(" ")[1];
            Integer idMenadzera = tokenService.getUserIdFromToken(token);

            if (idMenadzera == null) {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }

            restoranLoyaltyService.dodajPogodnostiZaRestoran(restoranLoyaltyDto, idMenadzera);

            return new ResponseEntity<>("Pogodnosti za restoran su uspesno dodate", HttpStatus.CREATED);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Došlo je do greske prilikom dodavanja pogodnosti", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}