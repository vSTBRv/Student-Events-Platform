package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;

import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendRegistrationEmail(String to, String user) {
        final String subject = "Witamy w Eventify!";
        String content = "Cześć " + user + ",\n\n" +
                "Dziękujemy za rejestracje w Eventify - platformie do zarządzania wydarzeniami studenckimi.\n\n" +
                "Od teraz możesz:\n" +
                "- przeglądać i zapisywać się na wydarzenia,\n" +
                "- tworzyć własne eventy (jeśli masz odpowiednie uprawnienia),\n" +
                "- zarządzać swoimi rejestracjami. \n\n" +
                "Pozdrawiamy, zespół Eventify.";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendEventRegistrationConfirmationEmail(String to, User user, Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(to);
        message.setSubject("Zapisano na wydarzenie: " + event.getName());

        String body = String.format("""
                Cześć %s,
                
                Potwierdzamy Twoje zgłoszenie na wydarzenie "%s".
                
                Termin: %s
                Miejsce: %s
                
                Szczegóły: %s
                
                Do zobaczenia!
                
                Pozdrawiamy,
                Zespół Eventify
                """,
                user.getUsername(),
                event.getName(),
                event.getStartDate(),
                event.getLocation().toString(),
                event.getDescription()
                );
        message.setText(body);
        mailSender.send(message);
    }

    public void sendEventUnregistrationConfirmationEmail(String to, User user, Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(to);
        message.setSubject("Rezygnacja z wydarzenia: " + event.getName());

        String body = String.format("""
                Cześć %s,
                
                Potwierdzamy, że zrezygnowałeś z udziału w wydarzeniu "%s".
                
                Termin: %s
                Miejsce: %s
                
                Szkoda Cię nie zobaczyć - mamy nadzieję, że spotkamy się przy innej okazji.
                
                Pozdrawiamy,
                Zespół Eventify.
                """,
                user.getUsername(),
                event.getName(),
                event.getStartDate(),
                event.getLocation().toString());

        message.setText(body);
        mailSender.send(message);
    }

    public void sendEventCreatedConfirmationEmail(User user, Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(user.getEmail());
        message.setSubject("Utworzono wydarzenie: " + event.getName());
        String body = String.format("""
                Cześć %s,
                
                Twoje wydarzenie "%s" zostało pomyślnie utworzone.
                
                Termin: %s
                Miejsce: %s
                Opis: %s
                
                Możesz zarządzać wydarzeniem na swoim koncie Eventify.
                
                Pozdrawiamy,
                Zespół Eventify.
                """,
                user.getEmail(),
                event.getName(),
                event.getStartDate(),
                event.getLocation().toString(),
                event.getDescription());

        message.setText(body);
        mailSender.send(message);
    }

    public void sendEventDeletedConfirmationEmail(User user, Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(user.getEmail());
        String body = String.format("""
                Cześć %s,
                
                Twoje wydarzenie "%s" zostało pomyślnie usunięte.
                
                Termin: %s
                Miejsce: %s
                Opis: %s
                
                Przykro nam, że Twoje wydarzenie nie dojdzie do skutku.
                
                Pozdrawiamy,
                Zespół Eventify.
                """,
                user.getFullName(),
                event.getStartDate(),
                event.getLocation().toString(),
                event.getDescription());

        message.setText(body);
        mailSender.send(message);
    }

    public void sendMessageToParticipants(Set<User> participants, Event event, String messageContent) {
        for(User user: participants) {
            String subject = "Nowa wiadomość dotycząca wydarzenia: " + event.getName();
            this.sendConfirmationEmail(user.getEmail(), subject, messageContent);
        }
    }
    public void sendEventFullNotification(User user, Event event) {
        String subject = "Nowa wiadomość dotycząca wydarzenia: " + event.getName();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(user.getEmail());
        String body = String.format("""
                Cześć %s,
                
                Twoje wydarzenie "%s" osiągneło limit uczestników.
                
                Pozdrawiamy,
                Zespół Eventify.
                """,
                user.getFullName(),
                event.getName());
        message.setText(body);
        mailSender.send(message);
    }

    public void sendAcceptedEmail(User owner, Event event) {
        String subject = "Twoje wydarzenie zostało zaakceptowane";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@student-events-platform.local");
        message.setTo(owner.getEmail());
        String body = String.format("""
                Cześć %s,
                
                Twoje wydarzenie "%s" zostało zaakceptowane.
                
                Pozdrawiamy,
                Zespół Evenify.""",
                owner.getFullName(),
                event.getName());
        message.setText(body);
        mailSender.send(message);
    }
}
