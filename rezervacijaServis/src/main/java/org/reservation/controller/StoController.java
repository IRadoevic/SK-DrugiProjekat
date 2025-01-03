package org.reservation.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @CheckSecurity(roles = {"MANAGER"})
    @PostMapping("/addSto")
    public ResponseEntity<Sto> addSto(@RequestHeader("Authorization") String authorization,
                                      @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

        //poprilicno sam sig da nam ovo ne treba ali sto da ne
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

    @CheckSecurity(roles = {"MANAGER"})
    @PutMapping("/updateSto/{id}")
    public ResponseEntity<Sto> updateSto(@RequestHeader("Authorization") String authorization,
                                         @PathVariable Long id,
                                         @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

        if (userId == null) {
            System.out.println("Unauthorized: User ID could not be extracted from token.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Sto sto = stoService.updateSto(id, stoDto, userId);
            System.out.println("Successfully updated Sto with ID: " + id);
            return new ResponseEntity<>(sto, HttpStatus.OK);
        } catch (ForbiddenException e) {
            System.out.println("Forbidden: User with ID " + userId + " does not have access to update Sto with ID: " + id);
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            System.out.println("Not Found: Sto with ID " + id + " does not exist.");
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Internal Server Error while updating Sto with ID: " + id);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
