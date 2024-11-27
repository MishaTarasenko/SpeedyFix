package ukma.speedyfix.controller;

import com.example.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukma.speedyfix.exception.FailedToSendEmailException;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Boolean> sendEmail(@RequestBody Map<String, String> body) {
        try {
            emailService.sendEmail(body.get("to"), body.get("subject"), body.get("body"));
            return ResponseEntity.ok(true);
        } catch (IOException e) {
            throw new FailedToSendEmailException(e.getMessage());
        }
    }
}
