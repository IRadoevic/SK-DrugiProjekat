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

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})


    @CheckSecurity(roles = {"menadzer"})
    @PostMapping("/addSto")
    public ResponseEntity<Sto> addSto(@RequestHeader("Authorization") String authorization,
                                      @RequestBody @Valid StoDto stoDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

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
        Integer userId = tokenService.getUserIdFromToken(token);

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
