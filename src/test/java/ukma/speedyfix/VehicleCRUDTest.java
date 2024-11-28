package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ukma.speedyfix.domain.type.EngineType;
import ukma.speedyfix.domain.type.TransmissionType;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.repositories.VehicleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VehicleCRUDTest extends BaseTest {

    @Autowired
    public VehicleCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                           UserRepository userRepository, VehicleRepository vehicleRepository, MockMvc mockMvc) {
        super(employeeRepository, customerRepository, userRepository, vehicleRepository, mockMvc);
    }

    @Test
    void adminCantCreateVehicleTest() throws Exception {
        mockMvc.perform(post("/user/api/vehicle")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void mechanicCantCreateVehicleTest() throws Exception {
        mockMvc.perform(post("/user/api/vehicle")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanCreateVehicleTest() throws Exception {
        mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk());
    }

    @Test
    void adminCantDeleteVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(delete("/user/api/vehicle/" + vehicleId)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void mechanicCantDeleteVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(delete("/user/api/vehicle/" + vehicleId)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanDeleteHisVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(delete("/user/api/vehicle/" + vehicleId)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCantUpdateVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(put("/user/api/vehicle")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getVehicleUpdateView(Integer.valueOf(vehicleId)))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void mechanicCantUpdateVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(put("/user/api/vehicle")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getVehicleUpdateView(Integer.valueOf(vehicleId)))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanUpdateHisVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(put("/user/api/vehicle")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getVehicleUpdateView(Integer.valueOf(vehicleId)))))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetAllVehiclesByCustomer() throws Exception {
        mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk());

        mockMvc.perform(get("/auth/api/vehicle/owner/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetAllVehiclesByCustomer() throws Exception {
        mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk());

        mockMvc.perform(get("/auth/api/vehicle/owner/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanGetAllHisVehicles() throws Exception {
        mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk());

        mockMvc.perform(get("/auth/api/vehicle/owner/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanViewVehicleById() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(get("/auth/api/vehicle/" + vehicleId)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanViewVehicleById() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(get("/auth/api/vehicle/" + vehicleId)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanViewHisVehicleById() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        mockMvc.perform(get("/auth/api/vehicle/" + vehicleId)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private VehicleView getVehicleView(Integer id) {
        return VehicleView.builder()
                .brand("Skoda")
                .model("Octavia")
                .yearOfRelease(2024)
                .engineType(EngineType.PETROL_NATURALLY_ASPIRATED)
                .displacement(2.5)
                .transmissionType(TransmissionType.CONTINUOUSLY_VARIABLE)
                .wheelRadius(19)
                .registrationNumber("KA8888KA")
                .ownerId(id)
                .build();
    }

    private VehicleView getVehicleUpdateView(Integer id) {
        return VehicleView.builder()
                .id(id)
                .brand("Audi")
                .model("Q8")
                .build();
    }
}
