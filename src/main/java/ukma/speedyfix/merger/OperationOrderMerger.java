package ukma.speedyfix.merger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.OperationOrderEntity;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.repositories.VehicleRepository;

import java.time.LocalDate;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class OperationOrderMerger {

    private final OperationRepository operationRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public void mergeCreate(OperationOrderEntity entity, OperationOrderView view) {
        entity.setStartDate(LocalDate.now());
        if (view.getOrderStatus() != null) {
            entity.setOrderStatus(view.getOrderStatus());
        }
        if (view.getEndDate() != null) {
            entity.setEndDate(view.getEndDate());
        }
        if (view.getOperationIds() != null && !view.getOperationIds().isEmpty()) {
            entity.setOperations(new HashSet<>(operationRepository.findAllById(view.getOperationIds())));
            if (entity.getOperations().isEmpty()) {
                throw new NoSuchEntityException("Can't find any of operations with ids: " + view.getOperationIds());
            }
        }
        if (view.getVehicleId() != null) {
            entity.setVehicle(vehicleRepository.findById(view.getVehicleId()).orElseThrow(
                    () -> new NoSuchEntityException("Vehicle with id " + view.getVehicleId() + " not found")
            ));
        }
        if (view.getCustomerId() != null) {
            entity.setCustomer(customerRepository.findById(view.getCustomerId()).orElseThrow(
                    () -> new NoSuchEntityException("Customer with id " + view.getCustomerId() + " not found")
            ));
        }
        if (view.getEmployeeIds() != null && !view.getEmployeeIds().isEmpty()) {
            entity.setEmployee(new HashSet<>(employeeRepository.findAllById(view.getEmployeeIds())));
            if (entity.getEmployee().isEmpty()) {
                throw new NoSuchEntityException("Can't find any of employee with ids: " + view.getEmployeeIds());
            }
        }
    }
}
