package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.repositories.*;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OperationCRUDTest extends BaseTest {

    @Autowired
    public OperationCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                             UserRepository userRepository, VehicleRepository vehicleRepository,
                             OperationRepository operationRepository, MockMvc mockMvc) {
        super(employeeRepository, customerRepository, userRepository, vehicleRepository, operationRepository, mockMvc);
    }

    @Test
    void adminCanCreateOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantCreateOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCantCreateOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCanDeleteEmployeeTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        mockMvc.perform(delete("/admin/api/operation/" + operationId)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantDeleteOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        mockMvc.perform(delete("/admin/api/operation/" + operationId)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCantDeleteOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        mockMvc.perform(delete("/admin/api/operation/" + operationId)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCanUpdateEmployeeTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        operation.setId(Integer.valueOf(operationId));
        operation.setPrice(100.0);

        mockMvc.perform(put("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantUpdateOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        operation.setId(Integer.valueOf(operationId));
        operation.setPrice(100.0);

        mockMvc.perform(put("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCantUpdateOperationTest() throws Exception {
        OperationView operation = new OperationView(null, "Wash", "Wash your car", 19.99, Set.of(mechanic.getId()));

        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        operation.setId(Integer.valueOf(operationId));
        operation.setPrice(100.0);

        mockMvc.perform(put("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().is4xxClientError());
    }

//
//    @Test
//    void allCanGetAllEmployees() throws Exception {
//        mockMvc.perform(get("/public/api/employee")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void allCanGetEmployeeById() throws Exception {
//        mockMvc.perform(get("/public/api/employee/" + admin.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
}
