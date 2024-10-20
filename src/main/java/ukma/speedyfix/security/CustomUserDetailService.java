package ukma.speedyfix.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        EmployeeEntity employee = employeeRepository.findByEmail(email).orElse(null);
        CustomerEntity customer = customerRepository.findByEmail(email).orElse(null);

        if (employee != null) {
            return new User(
                    employee.getUser().getEmail(),
                    employee.getUser().getPassword(),
                    Collections.singletonList(() -> "ROLE_" + "ADMIN")
            );
        } else if (customer != null) {
            return new User(
                    customer.getUser().getEmail(),
                    customer.getUser().getPassword(),
                    Collections.singletonList(() -> "ROLE_" + "USER")
            );
        } else {
            throw new UsernameNotFoundException("User with  email: {} not found");
        }
    }
}
