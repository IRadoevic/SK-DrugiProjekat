package org.reservation.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.reservation.domain.RestoranLoyalty;
import org.reservation.service.LoyaltyStatusKorisnikaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/rezervacijaLoyaltyStatus")
public class LoyaltyStatusKorisnikaController {
    // izlistavanje svih pogodnosti koje korisnik ima
    private final LoyaltyStatusKorisnikaService loyaltyStatusKorisnikaService;

    public LoyaltyStatusKorisnikaController(LoyaltyStatusKorisnikaService loyaltyStatusKorisnikaService) {
        this.loyaltyStatusKorisnikaService = loyaltyStatusKorisnikaService;
    }

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})


    // Endpoint za izlistavanje svih pogodnosti korisnika
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RestoranLoyalty>> getSvePogodnostiKorisnika(@PathVariable Long userId) {
        List<RestoranLoyalty> pogodnosti = loyaltyStatusKorisnikaService.svePogodnostiKojeJeKorisnikOstvario(userId);
        if (pogodnosti.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pogodnosti);
    }

}
