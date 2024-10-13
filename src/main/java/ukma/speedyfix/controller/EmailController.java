package ukma.speedyfix.controller;

import com.example.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail() {
        try {
            emailService.sendEmail("misha0106@gmail.com", "From SpeedyFix", "This is a test email.");
            return ResponseEntity.ok("Email sent successfully to " + "chekavic@gmail.com");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
