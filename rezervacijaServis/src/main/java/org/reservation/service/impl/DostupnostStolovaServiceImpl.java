package org.reservation.service.impl;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.retry.Retry;

import org.reservation.repository.*;
import org.reservation.domain.*;
import org.reservation.dto.*;
import org.reservation.exception.ForbiddenException;
import org.reservation.exception.NotFoundException;
import org.reservation.listener.helper.MessageHelper;
import org.reservation.mapper.DostupnostMapper;
import org.reservation.service.DostupnostStolovaService;

import org.reservation.service.LoyaltyStatusKorisnikaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;




@Service
public class DostupnostStolovaServiceImpl implements DostupnostStolovaService {
    private  StoRepository stoRepository;
    private  DostupnostStolovaRepository dostupnostRepository;
    private DostupnostMapper dostupnostMapper;
    private RestoranRepository restoranRepository;
    private RestTemplate userRestTemplate;
    private JmsTemplate jmsTemplate;
    //izbacila sam ih iz konstruktora jer sve zeznu
    @Value("${destination.incrementReservationCount}") //ovo je definisano u app.properties
    private String incrementReservationCount;

    @Value("${destination.decrementReservationCount}")
    private String decrementReservationCount;
    private MessageHelper messageHelper;
    private Retry userServiceRetry;
    private Bulkhead userServiceBulkhead;
    private RestoranLoyaltyRepository restoranLoyaltyRepository;
    private LoyaltyStatusKorisnikaService loyaltyStatusKorisnikaService;


    public DostupnostStolovaServiceImpl(StoRepository stoRepository,
                                        DostupnostStolovaRepository dostupnostRepository,
                                        DostupnostMapper dostupnostMapper,
                                        RestoranRepository restoranRepository,
                                        RestTemplate userRestTemplate, JmsTemplate jmsTemplate,
                                        MessageHelper messageHelper, Retry userServiceRetry,
                                        Bulkhead userServiceBulkhead,
                                        RestoranLoyaltyRepository restoranLoyaltyRepository,
                                        LoyaltyStatusKorisnikaService loyaltyStatusKorisnikaService) {
        this.stoRepository = stoRepository;
        this.dostupnostRepository = dostupnostRepository;
        this.dostupnostMapper = dostupnostMapper;
        this.restoranRepository = restoranRepository;
        this.userRestTemplate = userRestTemplate;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.userServiceRetry = userServiceRetry;
        this.userServiceBulkhead = userServiceBulkhead;
        this.restoranLoyaltyRepository = restoranLoyaltyRepository;
        this.loyaltyStatusKorisnikaService = loyaltyStatusKorisnikaService;
    }

    @Override
    public DostupnostStolova addDostupnost(DostupnostDto dostupnostDto, Integer menadzer, boolean isAdmin) {
        System.out.println(dostupnostDto.getSto());
        System.out.println(dostupnostDto.getSto().getId());
        Optional<Sto> stoOptional = stoRepository.findById(dostupnostDto.getSto().getId());
        Sto sto = stoOptional.orElseThrow(() -> new NotFoundException("Sto sa ID-jem " + dostupnostDto.getSto().getId() + " nije pronađen."));
        if (sto.getRestoran() == null) {
            throw new NotFoundException("Invalid restaurant.");
        }
        Restoran restoran = restoranRepository.findById(sto.getRestoran().getId())
                .orElseThrow(() -> new NotFoundException("Restoran  nije pronađen."));
        if (!isAdmin && restoran.getMenagerId() == null) {
            throw new NotFoundException("Invalid manager.");
        }
        if (!isAdmin && !restoran.getMenagerId().equals(menadzer)) {
            throw new ForbiddenException("Nemate prava da dodate sto u ovaj restoran.");
        }

        DostupnostStolova dostupnostStolova = dostupnostMapper.dostupnostDtoToDostupnost(dostupnostDto);
        dostupnostStolova.setSto(sto);
        return dostupnostRepository.save(dostupnostStolova);
    }

    @Override
    public DostupnostStolova updateDostupnost(Long id, UpateDostupnostDto updateDostupnostDto,Integer userId ) {
        DostupnostStolova dostupnostStolova = dostupnostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dostupnost sa ID-jem " + id + " nije pronađena."));
        Sto sto = stoRepository.findById(dostupnostStolova.getSto().getId()) .orElseThrow(() -> new NotFoundException("Dostupnost sa ID-jem " + id + " nije pronađena."));

        Restoran restoran = restoranRepository.findById(sto.getRestoran().getId())
                .orElseThrow(() -> new NotFoundException("Restoran  nije pronađen."));

        if (!restoran.getMenagerId().equals(userId)) {
            throw new ForbiddenException("Nemate prava da dodate sto u ovaj restoran.");
        }

        if (updateDostupnostDto.getDateTime() != null) {
            dostupnostStolova.setDatumVreme(updateDostupnostDto.getDateTime());
        }
        dostupnostStolova.setDostupnostStolova(updateDostupnostDto.isAvailable());

        return dostupnostRepository.save(dostupnostStolova);
    }

    @Override
    public List<DostupnostStolova> findAvailableTerminiByFilters(FilterDostupnostiDto filterDostupnostiDto) {
        return dostupnostRepository.findAvailableTerminiByFilters(
                filterDostupnostiDto.getTipKuhinje(),
                filterDostupnostiDto.getLokacija(),
                filterDostupnostiDto.getBrojOsoba(),
                filterDostupnostiDto.getDatumVreme()
        );
    }

    @Override
    public String rezervisi(RezervacijaDto rezervacijaDto) {
        DostupnostStolova dostupnost = dostupnostRepository
                .findAvailableByTableAndDate(rezervacijaDto.getIdStola(), rezervacijaDto.getDateTime())
                .orElseThrow(() -> new NotFoundException("Nema dostupnosti za sto u traženo vreme."));
        dostupnost.setDostupnostStolova(false);
        dostupnost.setUserId(rezervacijaDto.getIdKorisnika());
        dostupnostRepository.save(dostupnost);
        //dohvatimo br rezervacija
        Integer brRezervacija = Bulkhead.decorateSupplier(userServiceBulkhead, ()-> Retry.decorateSupplier(userServiceRetry, ()->getBrRezervacija(rezervacijaDto.getIdKorisnika())).get()).get();
        Restoran restoran = dostupnost.getSto().getRestoran();
        // nalazimo sve pogodnosti koje pruza restoran
        List<RestoranLoyalty> pogodnostiRestorana = restoranLoyaltyRepository.findByRestoran(restoran);
        // prolazimo kroz listu i gledamo uslov
        for (RestoranLoyalty pogodnost : pogodnostiRestorana) {
            if(pogodnost.getNagrada().equals("popust"))
            {
                // za sve rezervacije preko lupam 10 imas popust
                if(brRezervacija>=pogodnost.getUslov())
                {
                    loyaltyStatusKorisnikaService.dodajPogodnostKorisniku(rezervacijaDto.getIdKorisnika(),pogodnost);
                    // saljemo mejl menadzeru
                    UserDto userDto = getUserById(rezervacijaDto.getIdKorisnika());
                    PorukaDto porukaDtoPogodnost1 = new PorukaDto();
                    UserDto manager = getUserById(restoran.getMenagerId().longValue());
                    porukaDtoPogodnost1.setEmail(manager.getEmail());
                    porukaDtoPogodnost1.setTipNotifikacije("Klijent je dobio pogodnost");
                    // %ime  %koji restoran
                    porukaDtoPogodnost1.setParametri(List.of(pogodnost.getNagrada(), userDto.getUsername()));
                    jmsTemplate.convertAndSend("send_emails_queue",
                           messageHelper.createTextMessage(porukaDtoPogodnost1));

                }
            }
            else if(pogodnost.getNagrada().equals("pice") || pogodnost.getNagrada().equals("dezert"))
            {
                // ako je ta rezervacija 5 rezervacija dobijas pice
                if((brRezervacija+1)%pogodnost.getUslov()==0)
                {
                    loyaltyStatusKorisnikaService.dodajPogodnostKorisniku(rezervacijaDto.getIdKorisnika(),pogodnost);
                    // saljemo mejl menadzeru
                    UserDto userDto = getUserById(rezervacijaDto.getIdKorisnika());
                    PorukaDto porukaDtoPogodnost2 = new PorukaDto();
                    UserDto manager = getUserById(restoran.getMenagerId().longValue());
                    porukaDtoPogodnost2.setEmail(manager.getEmail());
                    porukaDtoPogodnost2.setTipNotifikacije("Klijent je dobio pogodnost");
                    // %ime  %koji restoran
                    porukaDtoPogodnost2.setParametri(List.of(pogodnost.getNagrada(), userDto.getUsername()));
                    jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDtoPogodnost2));


                }
            }
        }
        // posaljemo useru da se uveca br rezervacija
        jmsTemplate.convertAndSend(incrementReservationCount,
                messageHelper.createTextMessage(new IncrementReservationCountDto(rezervacijaDto.getIdKorisnika())));
        // obavestimo notification klijenta da je uspesno rezervisao
        PorukaDto porukaDto = new PorukaDto();
        UserDto userDto = getUserById(rezervacijaDto.getIdKorisnika());
        porukaDto.setEmail(userDto.getEmail());
        porukaDto.setTipNotifikacije("Slanje notifikacije kada je rezervacija uspešno napravljena");
        // %ime %prezime  %koji restoran
        porukaDto.setParametri(List.of(userDto.getFirstName(), userDto.getLastName(), restoran.getImeRestorana()));
        jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDto));
        // saljemmo i menadzeru da je rezervisao klijent
        PorukaDto porukaDto2 = new PorukaDto();
        UserDto manager = getUserById(restoran.getMenagerId().longValue());
        porukaDto2.setEmail(manager.getEmail());
        porukaDto2.setTipNotifikacije("Slanje notifikacije kada je rezervacija uspešno napravljena");
        // %ime  %koji restoran
        porukaDto2.setParametri(List.of(userDto.getFirstName(), userDto.getLastName(), restoran.getImeRestorana()));
        jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDto2));
        return "uspesno rezervisano";
    }

    private Integer getBrRezervacija(Long id) {
        try {
            String url = "http://localhost:8080/api/user/getBrRezervacija/" + id;
            ResponseEntity<UserBrRezervacijaDto> response = userRestTemplate.exchange(
                    url, HttpMethod.GET, null, UserBrRezervacijaDto.class);
            return response.getBody().getRezervacija();

        } catch (HttpClientErrorException e) {
            throw new NotFoundException("Korisnik nije pronađen za broj rezervacija.");
        } catch (Exception e) {
            throw new RuntimeException("Greška prilikom dohvatanja broja rezervacija.");
        }
    }

    private UserDto getUserById(Long id) {
        try {
            String url = "http://localhost:8080/api/user/getUser/" + id;
            ResponseEntity<UserDto> response = userRestTemplate.exchange(
                    url, HttpMethod.GET, null, UserDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new NotFoundException("Korisnik nije pronađen za ID: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Greška prilikom dohvatanja korisničkih podataka.");
        }
    }

    @Override
    public void otkazivanjeKlijent(RezervacijaDto rezervacijaDto) {
        // pronalazimo dostupnost
        DostupnostStolova dostupnost = dostupnostRepository
                .findReservation(rezervacijaDto.getIdStola(), rezervacijaDto.getDateTime(), rezervacijaDto.getIdKorisnika())
                .orElseThrow(() -> new NotFoundException("Nema dostupnosti za sto u traženo vreme."));
        System.out.println("nasao ga je");
        dostupnost.setDostupnostStolova(true);
        dostupnost.setUserId(null);
        dostupnostRepository.save(dostupnost);
        // saljemo da smanji broj rezervacija
        jmsTemplate.convertAndSend(decrementReservationCount,
                messageHelper.createTextMessage(new DecrementReservationCountDto(rezervacijaDto.getIdKorisnika())));
        // obavestavamo menadzera
        Restoran restoran = dostupnost.getSto().getRestoran();
        UserDto menadzer = getUserById(restoran.getMenagerId().longValue());
        PorukaDto porukaDto = new PorukaDto();
        porukaDto.setEmail(menadzer.getEmail());
        porukaDto.setTipNotifikacije("Slanje notifikacije za otkazivanje rezervacije");
        // rezervacija %broj stola u %Restoranu
        porukaDto.setParametri(List.of(String.valueOf(dostupnost.getSto().getId()), restoran.getImeRestorana()));
        jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDto));

    }

    @Override
    public void otkazivanjeMenadzer(RezervacijaDto rezervacijaDto) {
        DostupnostStolova dostupnost = dostupnostRepository
                .findReservationm(rezervacijaDto.getIdStola(), rezervacijaDto.getDateTime())
                .orElseThrow(() -> new NotFoundException("Nema dostupnosti za sto u traženo vreme."));
        Long idUsera = dostupnost.getUserId();
        dostupnost.setDostupnostStolova(true);
        dostupnost.setUserId(null);
        dostupnostRepository.save(dostupnost);
        // saljemo mejl korisniku
        Restoran restoran = dostupnost.getSto().getRestoran();
        UserDto user = getUserById(idUsera);
        PorukaDto porukaDto = new PorukaDto();
        porukaDto.setEmail(user.getEmail());
        porukaDto.setTipNotifikacije("Slanje notifikacije za otkazivanje rezervacije");
        // %username je otazao rezervacija %broj stola u %Restoranu
        porukaDto.setParametri(List.of(user.getUsername(), String.valueOf(dostupnost.getSto().getId()), restoran.getImeRestorana()));
        jmsTemplate.convertAndSend("send_emails_queue", messageHelper.createTextMessage(porukaDto));

    }

    @Override
    public List<DostupnostStolova> kroisnikoviTermini(long usersId) {
        return dostupnostRepository.findByUserId(usersId);
    }

    @Override
    public List<DostupnostStolova> restoranskiTermini(long userId) {
        return dostupnostRepository.findByManagerId((int) userId);
    }
}
