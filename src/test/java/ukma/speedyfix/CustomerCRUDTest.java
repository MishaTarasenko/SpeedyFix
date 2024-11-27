package ukma.speedyfix;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerCRUDTest {
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final MockMvc mockMvc;

    private EmployeeEntity mechanic;
    private EmployeeEntity admin;
    private CustomerEntity customer;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CustomerCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                            UserRepository userRepository, MockMvc mockMvc) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
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
                        "password123")
        );

        UserEntity secondUser = userRepository.saveAndFlush(
                new UserEntity(null,
                        "Bob",
                        "Johnson",
                        "bob.johnson@example.com",
                        "9876543210",
                        "securePass456")
        );

        UserEntity thirdUser = userRepository.saveAndFlush(
                new UserEntity(null,
                        "Carol",
                        "Williams",
                        "carol.williams@example.com",
                        "4567890123",
                        "Pa$$w0rd789")
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
                        EmployeeType.ADMIN,
                        secondUser,
                        null
                )
        );

        customer = customerRepository.saveAndFlush(new CustomerEntity(null, null, thirdUser));
    }

    @Test
    void testCreate() throws Exception {
        String newCustomerJson = """
        {
           "firstName": "John",
           "lastName": "Doe",
           "email": "john.doe@example.com",
           "telephoneNumber": "0506078710",
           "password": "password123"
        }
        """;

        mockMvc.perform(post("/public/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCustomerJson))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void adminCanGetAllCustomers() throws Exception {
//        mockMvc.perform(get("/admin/api/customer")
//                        .header("Authorization", "Bearer " + loginLikeAdmin())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
        System.out.println(loginLikeAdmin());
    }

    private String loginLikeAdmin() throws Exception {
        String loginJson = """
        {
           "username": "bob.johnson@example.com",
           "password": "securePass456"
        }
        """;

        MvcResult result = mockMvc.perform(post("/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(responseBody, JwtResponse.class);
        return jwtResponse.getToken();
    }

//    private String loginLikeMechanic() {
//
//    }
//
//    private String loginLikeAnotherCustomer() {
//
//    }
}
