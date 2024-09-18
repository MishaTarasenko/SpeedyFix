package ukma.speedyfix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.user.MyAuthService;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MyAuthService<UserEntity> authService;

    @GetMapping
    private void helloWorld() {
        authService.findByEmail("admin@example.com");
    }
}
