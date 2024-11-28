package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ukma.speedyfix.domain.type.EngineType;
import ukma.speedyfix.domain.type.OperationOrderStatusType;
import ukma.speedyfix.domain.type.TransmissionType;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.repositories.*;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OperationOrderCRUDTest extends BaseTest {

    @Autowired
    public OperationOrderCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                                  UserRepository userRepository, VehicleRepository vehicleRepository,
                                  OperationRepository operationRepository, OperationOrderRepository operationOrderRepository,
                                  MockMvc mockMvc) {
        super(employeeRepository, customerRepository, userRepository, vehicleRepository,
                operationRepository, operationOrderRepository, mockMvc);
    }

    @Test
    void adminCantCreateOperationOrderTest() throws Exception {
        Integer vehicleId = createVehicle();
        Integer operationId = createOperation();

        mockMvc.perform(post("/user/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getOperationOrderView(
                                operationId,
                                vehicleId,
                                customer.getId(),
                                mechanic.getId()
                        ))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void mechanicCantCreateOperationOrderTest() throws Exception {
        Integer vehicleId = createVehicle();
        Integer operationId = createOperation();

        mockMvc.perform(post("/user/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getOperationOrderView(
                                operationId,
                                vehicleId,
                                customer.getId(),
                                mechanic.getId()
                        ))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanCreateOperationOrderTest() throws Exception {
       createOperationOrder();
    }

    @Test
    void adminCanDeleteOperationOrderTest() throws Exception {
        Integer operationOrderId = createOperationOrder();

        mockMvc.perform(delete("/auth/api/operation/order/" + operationOrderId)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCantDeleteOperationOrderTest() throws Exception {
        Integer operationOrderId = createOperationOrder();

        mockMvc.perform(delete("/auth/api/operation/order/" + operationOrderId)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void customerCanDeleteOperationOrderTest() throws Exception {
        Integer operationOrderId = createOperationOrder();

        mockMvc.perform(delete("/auth/api/operation/order/" + operationOrderId)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanUpdateOperationOrderTest() throws Exception {
        mockMvc.perform(put("/auth/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOperationOrder()))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanUpdateOperationOrderTest() throws Exception {
        mockMvc.perform(put("/auth/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOperationOrder()))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanUpdateOperationOrderTest() throws Exception {
        mockMvc.perform(put("/auth/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOperationOrder()))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetOperationOrderByIdTest() throws Exception {
        Integer operationOrderId = createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/" + operationOrderId)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetOperationOrderByIdTest() throws Exception {
        Integer operationOrderId = createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/" + operationOrderId)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanGetOperationOrderByIdTest() throws Exception {
        Integer operationOrderId = createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/" + operationOrderId)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetOperationOrderByCustomerIdTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/customer/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetOperationOrderByCustomerIdTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/customer/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanGetOperationOrderByCustomerIdTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/customer/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetOperationOrderByEmployeeIdTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/admin/api/operation/order/employee/" + mechanic.getId())
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetOperationOrderByEmployeeIdTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/admin/api/operation/order/employee/" + mechanic.getId())
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCantGetOperationOrderByEmployeeIdTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/admin/api/operation/order/employee/" + mechanic.getId())
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void adminCanGetOperationOrderByCustomerIdAndStatusTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/" + OperationOrderStatusType.PENDING
                        + "/customer/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetOperationOrderByCustomerIdAndStatusTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/" + OperationOrderStatusType.PENDING
                        + "/customer/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCanGetOperationOrderByCustomerIdAndStatusTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/auth/api/operation/order/" + OperationOrderStatusType.PENDING
                        + "/customer/" + customer.getId())
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetOperationOrderByStatusTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/admin/api/operation/order/" + OperationOrderStatusType.PENDING)
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mechanicCanGetOperationOrderByStatusTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/admin/api/operation/order/" + OperationOrderStatusType.PENDING)
                        .header("Authorization", "Bearer " + loginLikeMechanic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void customerCantGetOperationOrderByStatusTest() throws Exception {
        createOperationOrder();

        mockMvc.perform(get("/admin/api/operation/order/" + OperationOrderStatusType.PENDING)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    private Integer createOperationOrder() throws Exception {
        Integer vehicleId = createVehicle();
        Integer operationId = createOperation();

        MvcResult result = mockMvc.perform(post("/user/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getOperationOrderView(
                                operationId,
                                vehicleId,
                                customer.getId(),
                                mechanic.getId()
                        ))))
                .andExpect(status().isOk())
                .andReturn();

        String operationOrderId = result.getResponse().getContentAsString();

        return Integer.valueOf(operationOrderId);
    }

    private String updateOperationOrder() throws Exception {
        Integer vehicleId = createVehicle();
        Integer operationId = createOperation();
        OperationOrderView view = getOperationOrderView(
                operationId,
                vehicleId,
                customer.getId(),
                mechanic.getId()
        );

        MvcResult result = mockMvc.perform(post("/user/api/operation/order")
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(view)))
                .andExpect(status().isOk())
                .andReturn();

        String operationOrderId = result.getResponse().getContentAsString();
        view.setId(Integer.valueOf(operationOrderId));
        view.setEndDate(LocalDate.now());

        return objectMapper.writeValueAsString(view);
    }

    private Integer createOperation() throws Exception {
        MvcResult result = mockMvc.perform(post("/admin/api/operation")
                        .header("Authorization", "Bearer " + loginLikeAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getOperationView(mechanic.getId()))))
                .andExpect(status().isOk())
                .andReturn();

        String operationId = result.getResponse().getContentAsString();

        return Integer.valueOf(operationId);
    }

    private Integer createVehicle() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + loginLikeAnotherCustomer())
                        .content(objectMapper.writeValueAsString(getVehicleView(customer.getId()))))
                .andExpect(status().isOk())
                .andReturn();
        String vehicleId = result.getResponse().getContentAsString();

        return Integer.valueOf(vehicleId);
    }

    private OperationOrderView getOperationOrderView(Integer operationId, Integer vehicleId,
                                                     Integer customerId, Integer employeeId) {
        return OperationOrderView.builder()
                .orderStatus(OperationOrderStatusType.PENDING)
                .operationIds(Set.of(operationId))
                .vehicleId(vehicleId)
                .customerId(customerId)
                .employeeIds(Set.of(employeeId))
                .build();
    }

    private OperationView getOperationView(Integer employeeId) {
        return OperationView.builder()
                .name("Wash")
                .description("Wash your car")
                .price(19.99)
                .employeeIds(Set.of(employeeId))
                .build();
    }

    private VehicleView getVehicleView(Integer customerId) {
        return VehicleView.builder()
                .brand("Skoda")
                .model("Octavia")
                .yearOfRelease(2024)
                .engineType(EngineType.PETROL_NATURALLY_ASPIRATED)
                .displacement(2.5)
                .transmissionType(TransmissionType.CONTINUOUSLY_VARIABLE)
                .wheelRadius(19)
                .registrationNumber("KA8888KA")
                .ownerId(customerId)
                .build();
    }
}
