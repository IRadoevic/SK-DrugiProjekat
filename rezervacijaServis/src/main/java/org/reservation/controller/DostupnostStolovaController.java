package org.reservation.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.reservation.domain.DostupnostStolova;
import org.reservation.domain.Sto;
import org.reservation.dto.DostupnostDto;
import org.reservation.dto.FilterDostupnostiDto;
import org.reservation.dto.RezervacijaDto;
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

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    @CheckSecurity(roles = {"menadzer"})
    @PostMapping("/add")
    public ResponseEntity<DostupnostStolova> addDostupnost(@RequestHeader("Authorization") String authorization,
                                                           @RequestBody @Valid DostupnostDto dostupnostDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            DostupnostStolova dostupnost = dostupnostStolovaService.addDostupnost(dostupnostDto, userId);
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
        Integer userId = tokenService.getUserIdFromToken(token);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            DostupnostStolova dostupnost = dostupnostStolovaService.updateDostupnost(id, updateDostupnostDto, userId);
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


    @PostMapping("/makeReservation")
    public ResponseEntity<String> makeReservation(@RequestBody @Valid RezervacijaDto rezervacijaDto) {
        try {
            dostupnostStolovaService.rezervisi(rezervacijaDto);
            return new ResponseEntity<>("Rezervacija je uspešno izvršena.", HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Došlo je do greške pri rezervaciji.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"klijent"})
    @PostMapping("/cancelReservation/klijent")
    public ResponseEntity<String> cancelReservationKlijent(@RequestHeader("Authorization") String authorization,
                                                           @RequestBody @Valid RezervacijaDto rezervacijaDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            dostupnostStolovaService.otkazivanjeKlijent(rezervacijaDto);
            return new ResponseEntity<>("Rezervacija je uspešno otkazana.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Došlo je do greške prilikom otkazivanja rezervacije.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"menadzer"})
    @PostMapping("/cancelReservation/menadzer")
    public ResponseEntity<String> cancelReservationMenadzer(@RequestHeader("Authorization") String authorization,
                                                            @RequestBody @Valid RezervacijaDto rezervacijaDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            dostupnostStolovaService.otkazivanjeMenadzer(rezervacijaDto);
            return new ResponseEntity<>("Rezervacija je uspešno otkazana od strane menadžera.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Došlo je do greške prilikom otkazivanja rezervacije.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
