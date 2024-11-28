package ukma.speedyfix;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.response.JwtResponse;
import ukma.speedyfix.domain.type.EmployeeType;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.repositories.VehicleRepository;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BaseTest {

    public final EmployeeRepository employeeRepository;
    public final CustomerRepository customerRepository;
    public final UserRepository userRepository;
    public final VehicleRepository vehicleRepository;
    public final MockMvc mockMvc;

    public EmployeeEntity mechanic;
    public EmployeeEntity admin;
    public CustomerEntity customer;

    public ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public BaseTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                            UserRepository userRepository, VehicleRepository vehicleRepository, MockMvc mockMvc) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void setUp() {
        UserEntity firstUser = userRepository.saveAndFlush(
                new UserEntity(null,
                        "Alice",
                        "Smith",
                        "alice.smith@example.com",
                        "1234567890",
                        passwordEncoder.encode("password123"))
        );

        UserEntity secondUser = userRepository.saveAndFlush(
                new UserEntity(null,
                        "Bob",
                        "Johnson",
                        "bob.johnson@example.com",
                        "9876543210",
                        passwordEncoder.encode("securePass456"))
        );

        UserEntity thirdUser = userRepository.saveAndFlush(
                new UserEntity(null,
                        "Carol",
                        "Williams",
                        "carol.williams@example.com",
                        "4567890123",
                        passwordEncoder.encode("Pa$$w0rd789"))
        );

        mechanic = employeeRepository.saveAndFlush(
                new EmployeeEntity(
                        null,
                        "Test",
                        EmployeeType.ADMIN,
                        firstUser,
                        null
                )
        );

        admin = employeeRepository.saveAndFlush(
                new EmployeeEntity(
                        null,
                        "Test",
                        EmployeeType.MECHANIC,
                        secondUser,
                        null
                )
        );

        customer = customerRepository.saveAndFlush(new CustomerEntity(null, new ArrayList<>(), thirdUser));
    }

    @AfterEach
    public void clear() {
        vehicleRepository.deleteAll();
        employeeRepository.deleteAll();
        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    public String loginLikeAdmin() throws Exception {
        String loginJson = """
        {
           "username": "alice.smith@example.com",
           "password": "password123"
        }
        """;

        MvcResult result = mockMvc.perform(post("/public/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(responseBody, JwtResponse.class);
        return jwtResponse.getToken();
    }

    public String loginLikeMechanic() throws Exception {
        String loginJson = """
        {
           "username": "bob.johnson@example.com",
           "password": "securePass456"
        }
        """;

        MvcResult result = mockMvc.perform(post("/public/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(responseBody, JwtResponse.class);
        return jwtResponse.getToken();
    }

    public String loginLikeAnotherCustomer() throws Exception {
        String loginJson = """
        {
           "username": "carol.williams@example.com",
           "password": "Pa$$w0rd789"
        }
        """;

        MvcResult result = mockMvc.perform(post("/public/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(responseBody, JwtResponse.class);
        return jwtResponse.getToken();
    }
}
