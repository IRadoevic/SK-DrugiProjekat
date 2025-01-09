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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/dostupnostStolova")
public class DostupnostStolovaController {
    private DostupnostStolovaService dostupnostStolovaService;
    private TokenService tokenService;


    public DostupnostStolovaController(DostupnostStolovaService dostupnostStolovaService, TokenService tokenService) {
        this.dostupnostStolovaService = dostupnostStolovaService;
        this.tokenService = tokenService;
    }

    @CheckSecurity(roles = {"MANAGER", "ADMIN"})
    @PostMapping("/add")
    public ResponseEntity<DostupnostStolova> addDostupnost(@RequestHeader("Authorization") String authorization,
                                                           @RequestBody @Valid DostupnostDto dostupnostDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);
        boolean isAdmin = tokenService.getUserRoleFromToken(token).equals("ADMIN");
        if (!isAdmin && userId == null) {
            System.out.println("Unauthorized: User ID could not be extracted from the token.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            DostupnostStolova dostupnost = dostupnostStolovaService.addDostupnost(dostupnostDto, userId, isAdmin);
            System.out.println("Successfully added DostupnostStolova. User ID: " + userId);
            return new ResponseEntity<>(dostupnost, HttpStatus.CREATED);
        } catch (ForbiddenException e) {
            System.out.println("Forbidden: User with ID " + userId + " is not allowed to add DostupnostStolova.");
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            System.out.println("Not Found: Related entity for DostupnostStolova not found. User ID: " + userId);
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Internal Server Error while adding DostupnostStolova. User ID: " + userId);
            e.printStackTrace(); // Prints the full stack trace for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CheckSecurity(roles = {"MANAGER", "ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<DostupnostStolova> updateDostupnost(@RequestHeader("Authorization") String authorization,
                                                              @PathVariable Long id,
                                                              @RequestBody @Valid UpateDostupnostDto updateDostupnostDto) {
        //ovaj id koji uzima je id iz tabele dostupnosti, mozda ce posle za impl trebati da bude id stola
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
    @CheckSecurity(roles = {"USER", "ADMIN"})
    @GetMapping("/filter")
    public ResponseEntity<List<DostupnostStolova>> filterTermine(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) String tipKuhinje,
            @RequestParam(required = false) String lokacija,
            @RequestParam(required = false) Integer brojOsoba,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime datumVreme) {

        // Call the service layer with the extracted parameters
        FilterDostupnostiDto filterDto = new FilterDostupnostiDto();
        filterDto.setTipKuhinje(tipKuhinje);
        filterDto.setLokacija(lokacija);
        filterDto.setBrojOsoba(brojOsoba);
        filterDto.setDatumVreme(datumVreme);

        // Pass the DTO to the service layer
        List<DostupnostStolova> termini = dostupnostStolovaService.findAvailableTerminiByFilters(filterDto);
        return new ResponseEntity<>(termini, HttpStatus.OK);
    }


    @CheckSecurity(roles = {"USER", "ADMIN"})
    @GetMapping("/usersReservations")
    public ResponseEntity<List<DostupnostStolova>> uzmiKorisnikoveTermine(
            @RequestHeader("Authorization") String authorization) {
        String aut = authorization.split(" ")[1];
        int id = tokenService.getUserIdFromToken(aut);
        List<DostupnostStolova> termini = dostupnostStolovaService.kroisnikoviTermini(id);
        return new ResponseEntity<>(termini, HttpStatus.OK);
    }

    @CheckSecurity(roles = {"MANAGER", "ADMIN"})
    @GetMapping("/managersReservations")
    public ResponseEntity<List<DostupnostStolova>> uzmiRestoranskeTermine(
            @RequestHeader("Authorization") String authorization) {
        String aut = authorization.split(" ")[1];
        int id = tokenService.getUserIdFromToken(aut);
        List<DostupnostStolova> termini = dostupnostStolovaService.restoranskiTermini(Long.valueOf(id));
        return new ResponseEntity<>(termini, HttpStatus.OK);
    }


    @PostMapping("/makeReservation")
    public ResponseEntity<String> makeReservation(@RequestHeader("Authorization") String authorization,
                                                  @RequestBody @Valid RezervacijaDto rezervacijaDto) {
        try {
            String aut = authorization.split(" ")[1];
            rezervacijaDto.setIdKorisnika(Long.valueOf(tokenService.getUserIdFromToken(aut)));
            dostupnostStolovaService.rezervisi(rezervacijaDto);
            return new ResponseEntity<>("Rezervacija je uspešno izvršena.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Došlo je do greške pri rezervaciji." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"USER", "ADMIN"})
    @PostMapping("/cancelReservation/user")
    public ResponseEntity<String> cancelReservationKlijent(@RequestHeader("Authorization") String authorization,
                                                           @RequestBody @Valid RezervacijaDto rezervacijaDto) {
        String token = authorization.split(" ")[1];
        Integer userId = tokenService.getUserIdFromToken(token);

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        rezervacijaDto.setIdKorisnika(Long.valueOf(userId));

        try {
            dostupnostStolovaService.otkazivanjeKlijent(rezervacijaDto);
            return new ResponseEntity<>("Rezervacija je uspešno otkazana.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Došlo je do greške prilikom otkazivanja rezervacije.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CheckSecurity(roles = {"MANAGER", "ADMIN"})
    @PostMapping("/cancelReservation/manager")
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
