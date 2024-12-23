package org.reservation.controller;

import io.jsonwebtoken.Claims;
import org.reservation.domain.Sto;
import org.reservation.dto.StoDto;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.security.CheckSecurity;
import org.reservation.security.service.TokenService;
import org.reservation.service.RestoranService;
import org.reservation.service.StoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sto")
public class StoController {
    private StoService stoService;
    private  TokenService tokenService;

    public StoController(StoService stoService, TokenService tokenService) {
        this.stoService = stoService;
        this.tokenService = tokenService;
    }
    @CheckSecurity(roles = {"menadzer"})
    @PostMapping("/addSto")
    public ResponseEntity<Sto> addSto(@RequestHeader("Authorization") String authorization,
                                      @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);

        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Integer userId = claims.get("id", Integer.class);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Sto sto = stoService.addSto(stoDto, userId);
            return new ResponseEntity<>(sto, HttpStatus.CREATED);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"menadzer"})
    @PutMapping("/updateSto/{id}")
    public ResponseEntity<Sto> updateSto(@RequestHeader("Authorization") String authorization,
                                         @PathVariable Long id,
                                         @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);

        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Integer userId = claims.get("id", Integer.class);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Sto sto = stoService.updateSto(id, stoDto, userId);
            return new ResponseEntity<>(sto, HttpStatus.OK);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
