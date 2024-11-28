package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ukma.speedyfix.domain.EmployeeCreationWrapper;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.view.EmployeeView;
import ukma.speedyfix.repositories.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeCRUDTest extends BaseTest{

    @Autowired
    public EmployeeCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                            UserRepository userRepository, VehicleRepository vehicleRepository,
                            OperationRepository operationRepository, MockMvc mockMvc) {
        super(employeeRepository, customerRepository, userRepository, vehicleRepository, operationRepository, mockMvc);
    }

    @Test
    void adminCanCreateEmployeeTest() throws Exception {
        mockMvc.perform(post("/admin/api/employee")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_EMPLOYEE_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantCreateEmployeeTest() throws Exception {
        mockMvc.perform(post("/admin/api/employee")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_EMPLOYEE_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCantCreateEmployeeTest() throws Exception {
        mockMvc.perform(post("/admin/api/employee")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_EMPLOYEE_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCanDeleteEmployeeTest() throws Exception {
        Integer id = employeeRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/admin/api/employee/" + id)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantDeleteEmployeeTest() throws Exception {
        Integer id = employeeRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/admin/api/employee/" + id)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCantDeleteEmployeeTest() throws Exception {
        Integer id = employeeRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/admin/api/employee/" + id)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCanUpdateEmployeeTest() throws Exception {
        EmployeeCreationWrapper wrapper = new EmployeeCreationWrapper(
                new EmployeeView(mechanic.getId(), null, null, null),
                new UserEntity(null, "Oleksiy", null, null, null, null)
        );
        String json = objectMapper.writeValueAsString(wrapper);

        mockMvc.perform(put("/admin/api/employee")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantUpdateEmployeeTest() throws Exception {
        EmployeeCreationWrapper wrapper = new EmployeeCreationWrapper(
                new EmployeeView(mechanic.getId(), null, null, null),
                new UserEntity(null, "Oleksiy", null, null, null, null)
        );
        String json = objectMapper.writeValueAsString(wrapper);

        mockMvc.perform(put("/admin/api/employee")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCantUpdateEmployeeTest() throws Exception {
        EmployeeCreationWrapper wrapper = new EmployeeCreationWrapper(
                new EmployeeView(mechanic.getId(), null, null, null),
                new UserEntity(null, "Oleksiy", null, null, null, null)
        );
        String json = objectMapper.writeValueAsString(wrapper);

        mockMvc.perform(put("/admin/api/employee")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void allCanGetAllEmployees() throws Exception {
        mockMvc.perform(get("/public/api/employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void allCanGetEmployeeById() throws Exception {
        mockMvc.perform(get("/public/api/employee/" + admin.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static final String NEW_EMPLOYEE_JSON = """
            {
                "employee": {
                    "position": "Wheel master",
                    "type": "MECHANIC"
                },
                "user": {
                   "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "telephoneNumber": "0506078710",
                    "password": "password123"
                }
            }
        """;
}
