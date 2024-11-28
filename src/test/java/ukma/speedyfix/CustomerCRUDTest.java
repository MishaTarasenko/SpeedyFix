package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ukma.speedyfix.repositories.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerCRUDTest extends BaseTest {

    @Autowired
    public CustomerCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                            UserRepository userRepository, VehicleRepository vehicleRepository,
                            OperationRepository operationRepository, OperationOrderRepository operationOrderRepository,
                            MockMvc mockMvc) {
        super(employeeRepository, customerRepository, userRepository, vehicleRepository,
                operationRepository, operationOrderRepository, mockMvc);
    }

    @Test
    void testCreate() throws Exception {
        mockMvc.perform(post("/public/api/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_CUSTOMER_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetAllCustomers() throws Exception {
        mockMvc.perform(get("/admin/api/customer")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetAllCustomers() throws Exception {
        mockMvc.perform(get("/admin/api/customer")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCantGetAllCustomers() throws Exception {
        mockMvc.perform(get("/admin/api/customer")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCanViewCustomerById() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(get("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanViewCustomerById() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(get("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanViewHimById() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(get("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCantViewAnotherCustomerById() throws Exception {
        MvcResult result = mockMvc.perform(post("/public/api/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_CUSTOMER_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String customerId = result.getResponse().getContentAsString();

        mockMvc.perform(get("/auth/api/customer/" + customerId)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCantUpdateCustomer() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(put("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_CUSTOMER_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void mechanicCantUpdateCustomer() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(put("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_CUSTOMER_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanUpdateHimself() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(put("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_CUSTOMER_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCantDeleteCustomer() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_CUSTOMER_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void mechanicCantDeleteCustomer() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_CUSTOMER_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanDeleteHimself() throws Exception {
        Integer id = customerRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/auth/api/customer/" + id)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_CUSTOMER_JSON))
                .andExpect(status().isOk());
    }

    private static final String NEW_CUSTOMER_JSON = """
        {
           "firstName": "John",
           "lastName": "Doe",
           "email": "john.doe@example.com",
           "telephoneNumber": "0506078710",
           "password": "password123"
        }
        """;

    private static final String UPDATE_CUSTOMER_JSON = """
        {
            "firstName": "Oleksiy"
        }
        """;
}
