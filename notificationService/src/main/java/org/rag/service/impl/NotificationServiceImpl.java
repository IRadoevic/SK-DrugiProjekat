package org.rag.service.impl;

import org.rag.domain.Notification;
import org.rag.domain.NotificationType;
import org.rag.dto.FilterNotificationDto;
import org.rag.dto.NotifikacijaDto;
import org.rag.dto.PorukaDto;
import org.rag.exeption.NotFoundException;
import org.rag.mapper.NotifikacijaMapper;
import org.rag.repository.NotificationRepository;
import org.rag.repository.NotificationTypeRepository;
import org.rag.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationServiceImpl implements NotificationService {
    NotificationTypeRepository notificationTypeRepository;
    NotifikacijaMapper notifikacijaMapper;
    NotificationRepository notificationRepository;

    @Autowired
    public JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void obradiPoruku(PorukaDto porukaDto) {
        NotifikacijaDto notifikacijaDto = new NotifikacijaDto();
        notifikacijaDto.setVremeSlanja(LocalDateTime.now());


        if(porukaDto.getTipNotifikacije().equals("Slanje aktivacionog imejla"))
        {
            NotificationType notificationType = notificationTypeRepository.findByTip(porukaDto.getTipNotifikacije()).orElseThrow(() -> new NotFoundException("Notifikacija not found"));
           // neka rnd funkcija koju je gpt izgenerisao da menja % sa parametrima :))))))))
            String emailTekst = generateMessage(notificationType.getTekst(), porukaDto.getParametri());
            // doda da je poslao email
            notifikacijaDto.setEmail(porukaDto.getEmail());
            notifikacijaDto.setNotificationType(notificationType);
            notifikacijaDto.setTekst(emailTekst);
            dodajNotifikaciju(notifikacijaDto);
            // poziva da se posalje
            sendEmail(porukaDto.getEmail(), "Aktiviranje naloga",emailTekst);

        }
        else if(porukaDto.getTipNotifikacije().equals("Slanje imejla za promenu lozinke")){

            NotificationType notificationType = notificationTypeRepository.findByTip(porukaDto.getTipNotifikacije()).orElseThrow(() -> new NotFoundException("Notifikacija not found"));
            String emailTekst = generateMessage(notificationType.getTekst(), porukaDto.getParametri());
            notifikacijaDto.setEmail(porukaDto.getEmail());
            notifikacijaDto.setNotificationType(notificationType);
            notifikacijaDto.setTekst(emailTekst);
            dodajNotifikaciju(notifikacijaDto);
            sendEmail(porukaDto.getEmail(), "Promena lozinke",emailTekst);

        }
        else if(porukaDto.getTipNotifikacije().equals("Slanje notifikacije kada je rezervacija uspešno napravljena")){

            NotificationType notificationType = notificationTypeRepository.findByTip(porukaDto.getTipNotifikacije()).orElseThrow(() -> new NotFoundException("Notifikacija not found"));
            String emailTekst = generateMessage(notificationType.getTekst(), porukaDto.getParametri());
            notifikacijaDto.setEmail(porukaDto.getEmail());
            notifikacijaDto.setNotificationType(notificationType);
            notifikacijaDto.setTekst(emailTekst);
            dodajNotifikaciju(notifikacijaDto);
            sendEmail(porukaDto.getEmail(), "Uspesna rezervaija",emailTekst);

        }
        else if(porukaDto.getTipNotifikacije().equals("Slanje notifikacije za otkazivanje rezervacije")){

            NotificationType notificationType = notificationTypeRepository.findByTip(porukaDto.getTipNotifikacije()).orElseThrow(() -> new NotFoundException("Notifikacija not found"));
            String emailTekst = generateMessage(notificationType.getTekst(), porukaDto.getParametri());

            notifikacijaDto.setEmail(porukaDto.getEmail());
            notifikacijaDto.setNotificationType(notificationType);
            notifikacijaDto.setTekst(emailTekst);
            dodajNotifikaciju(notifikacijaDto);

            sendEmail(porukaDto.getEmail(), "Otkazivanje rezervacije",emailTekst);

        }
        else if(porukaDto.getTipNotifikacije().equals("Slanje podsetnika pre rezervisanog termina")){

            NotificationType notificationType = notificationTypeRepository.findByTip(porukaDto.getTipNotifikacije()).orElseThrow(() -> new NotFoundException("Notifikacija not found"));
            String emailTekst = generateMessage(notificationType.getTekst(), porukaDto.getParametri());
            notifikacijaDto.setEmail(porukaDto.getEmail());
            notifikacijaDto.setNotificationType(notificationType);
            notifikacijaDto.setTekst(emailTekst);
            dodajNotifikaciju(notifikacijaDto);
            sendEmail(porukaDto.getEmail(), "Podsetnik za rezervaciju",emailTekst);

        }
        else if(porukaDto.getTipNotifikacije().equals("Klijent je dobio pogodnost")){

            NotificationType notificationType = notificationTypeRepository.findByTip(porukaDto.getTipNotifikacije()).orElseThrow(() -> new NotFoundException("Notifikacija not found"));
            String emailTekst = generateMessage(notificationType.getTekst(), porukaDto.getParametri());
            notifikacijaDto.setEmail(porukaDto.getEmail());
            notifikacijaDto.setNotificationType(notificationType);
            notifikacijaDto.setTekst(emailTekst);
            dodajNotifikaciju(notifikacijaDto);
            sendEmail(porukaDto.getEmail(), "Ostvarena pogodnost",emailTekst);

        }
    }

    @Override
    public void dodajNotifikaciju(NotifikacijaDto notifikacijaDto) {
      notificationRepository.save( notifikacijaMapper.dtoToNotification(notifikacijaDto));
    }

    @Override
    public List<Notification> sveEmailove(FilterNotificationDto filterNotificationDto) {
        return notificationRepository.findAllEmailByFilter(filterNotificationDto.getTip(), filterNotificationDto.getStartDate(), filterNotificationDto.getEndDate());
    }

    @Override
    public List<Notification> userEmailove(Long id, FilterNotificationDto filterNotificationDto) {
        return notificationRepository.findUserEmailByFilter(filterNotificationDto.getTip(), filterNotificationDto.getEmail(), filterNotificationDto.getStartDate(), filterNotificationDto.getEndDate());
    }

    private static String generateMessage(String template, List<String> values) {
        StringBuilder result = new StringBuilder();
        int valueIndex = 0;

        // Pretražuješ tekst i menjaš % sa vrednostima iz liste
        for (int i = 0; i < template.length(); i++) {
            if (template.charAt(i) == '%' && valueIndex < values.size()) {
                // Zamenjuje % sa vrednostima iz liste
                result.append(values.get(valueIndex));
                valueIndex++;  // Prelaziš na sledeću vrednost u listi
            } else {
                // Dodaješ karakter iz šablona u rezultat ako nije %
                result.append(template.charAt(i));
            }
        }

        // Ako ima više vrednosti u listi nego % u šablonu, samo ih ignoriši
        return result.toString();
    }


}
