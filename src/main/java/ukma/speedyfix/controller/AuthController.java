package ukma.speedyfix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.response.JwtResponse;
import ukma.speedyfix.domain.view.LoginView;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.security.JwtTokenProvider;
import ukma.speedyfix.security.SecurityContextAccessor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody @Valid LoginView loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(((UserDetails) authentication.getPrincipal()).getUsername());
        EmployeeEntity employee = employeeRepository.findByEmail(loginRequest.getUsername()).orElse(null);
        CustomerEntity customer = customerRepository.findByEmail(loginRequest.getUsername()).orElse(null);

        if (employee != null) {
            return ResponseEntity.ok(new JwtResponse(jwt, SecurityContextAccessor.getRole(), employee.getId()));
        } else {
            return ResponseEntity.ok(new JwtResponse(jwt, SecurityContextAccessor.getRole(), customer.getId()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Successfully logged out");
    }
}
