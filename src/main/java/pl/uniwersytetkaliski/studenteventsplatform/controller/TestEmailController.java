package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.uniwersytetkaliski.studenteventsplatform.service.NotificationService;


@RestController
@RequestMapping("/api/test")
public class TestEmailController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/send-mail")
    public String sendMail(@RequestParam String email) {
        notificationService.sendConfirmationEmail(
                email,
                "Testowy mail z aplikacji",
                "Cześć, to jest testowy email z aplikacji Student Events Platform"
        );

        return "E-mail wysłany na " + email;
    }
}
