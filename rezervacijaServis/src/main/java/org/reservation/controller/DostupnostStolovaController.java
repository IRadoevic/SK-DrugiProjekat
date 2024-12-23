package org.reservation.controller;

import io.jsonwebtoken.Claims;
import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Sto;
import org.reservation.dto.DostupnostDto;
import org.reservation.dto.FilterDostupnostiDto;
import org.reservation.dto.UpateDostupnostDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.security.CheckSecurity;
import org.reservation.security.service.TokenService;
import org.reservation.service.DostupnostStolovaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dostupnostStolova")
public class DostupnostStolovaController {
    private DostupnostStolovaService dostupnostStolovaService;
    private TokenService tokenService;


    public DostupnostStolovaController(DostupnostStolovaService dostupnostStolovaService, TokenService tokenService) {
        this.dostupnostStolovaService = dostupnostStolovaService;
        this.tokenService = tokenService;
    }
    @CheckSecurity(roles = {"menadzer"})
    @PostMapping("/add")
    public ResponseEntity<DostupnostStolova> addDostupnost(@RequestHeader("Authorization") String authorization,
                                                           @RequestBody @Valid DostupnostDto dostupnostDto) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);

        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Integer menadzerId = claims.get("id", Integer.class);
        if (menadzerId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            DostupnostStolova dostupnost = dostupnostStolovaService.addDostupnost(dostupnostDto, menadzerId);
            return new ResponseEntity<>(dostupnost, HttpStatus.CREATED);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"menadzer"})
    @PutMapping("/update/{id}")
    public ResponseEntity<DostupnostStolova> updateDostupnost(@RequestHeader("Authorization") String authorization,
                                                              @PathVariable Long id,
                                                              @RequestBody @Valid UpateDostupnostDto updateDostupnostDto) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);

        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Integer menadzerId = claims.get("id", Integer.class);
        if (menadzerId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            DostupnostStolova dostupnost = dostupnostStolovaService.updateDostupnost(id, updateDostupnostDto, menadzerId);
            return new ResponseEntity<>(dostupnost, HttpStatus.OK);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CheckSecurity(roles = {"klijent"})
    @GetMapping("/filter")
    public ResponseEntity<List<DostupnostStolova>> filterTermine(@RequestBody FilterDostupnostiDto filterDto) {
        List<DostupnostStolova> termini = dostupnostStolovaService.findAvailableTerminiByFilters(filterDto);
        return new ResponseEntity<>(termini, HttpStatus.OK);
    }



}
