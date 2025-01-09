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

    @CheckSecurity(roles = {"MANAGER", "ADMIN"})
    @PostMapping("/addSto")
    public ResponseEntity<Sto> addSto(@RequestHeader("Authorization") String authorization,
                                      @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);
        try {
            String jwta[] = authorization.split(" ");
            String jwt = jwta[1];
            Sto sto = stoService.addSto(stoDto, userId, tokenService.getUserRoleFromToken(jwt).equals("ADMIN"));
            return new ResponseEntity<>(sto, HttpStatus.OK);
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"MANAGER", "ADMIN"})
    @PutMapping("/updateSto/{id}")
    public ResponseEntity<Sto> updateSto(@RequestHeader("Authorization") String authorization,
                                         @PathVariable Long id,
                                         @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);
        String role = tokenService.getUserRoleFromToken(token);
        if (!role.equals("ADMIN") && userId == null) {
            System.out.println("Unauthorized: User ID could not be extracted from token.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Sto sto = stoService.updateSto(id, stoDto, userId, role.equals("ADMIN"));
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
