package ukma.speedyfix.controller;

import com.example.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Boolean> sendEmail(@RequestBody Map<String, String> body) {
        try {
            emailService.sendEmail(body.get("to"), body.get("subject"), body.get("body"));
            return ResponseEntity.ok(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
