package ukma.speedyfix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ukma.speedyfix.repositories.*;

public class OperationCRUDTest extends BaseTest {

    @Autowired
    public OperationCRUDTest(EmployeeRepository employeeRepository, CustomerRepository customerRepository,
                             UserRepository userRepository, VehicleRepository vehicleRepository,
                             OperationRepository operationRepository, MockMvc mockMvc) {
        super(employeeRepository, customerRepository, userRepository, vehicleRepository, operationRepository, mockMvc);
    }


}
