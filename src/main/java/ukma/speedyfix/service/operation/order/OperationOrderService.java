package ukma.speedyfix.service.operation.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.entity.OperationOrderEntity;
import ukma.speedyfix.domain.response.OperationOrderResponse;
import ukma.speedyfix.domain.type.OperationOrderStatusType;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.merger.OperationOrderMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.OperationOrderRepository;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.security.SecurityContextAccessor;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;
import ukma.speedyfix.service.customer.CustomerService;
import ukma.speedyfix.service.operation.OperationService;
import ukma.speedyfix.service.vehicle.VehicleService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationOrderService implements MyService<OperationOrderEntity, OperationOrderView, Integer>, MyOperationOrderService {

    private final MyValidator<OperationOrderEntity> validator;
    private final OperationOrderRepository repository;
    private final OperationRepository operationRepository;
    private final EmployeeRepository employeeRepository;
    private final OperationOrderMerger merger;
    private final OperationService operationService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;

    @Override
    public OperationOrderEntity getById(Integer id) {
        OperationOrderEntity entity = repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException("Operation order with id " + id + " not found")
        );
        validator.validForView(entity);
        return entity;
    }

    public OperationOrderResponse getResponseById(Integer id) {
        return buildResponse(getById(id));
    }

    public List<OperationOrderResponse> getListByCustomerId(Integer customerId) {
        List<OperationOrderEntity> response = repository.findAllByCustomerId(customerId);
        for (OperationOrderEntity operationOrderEntity : response) {
            validator.validForView(operationOrderEntity);
        }
        return response.stream().map(this::buildResponse).toList();
    }

    public List<OperationOrderResponse> getListByEmployeeId(Integer employeeId) {
        if (!SecurityContextAccessor.getRole().equals("ROLE_ADMIN") && !SecurityContextAccessor.getRole().equals("ROLE_MECHANIC")) {
            throw new ValidationException("You do not have permission");
        }
        return repository.findAllByEmployeeId(employeeId).stream().map(this::buildResponse).toList();
    }

    public List<OperationOrderResponse> getListByStatusAndCustomerId(OperationOrderStatusType status, Integer customerId) {
        List<OperationOrderEntity> response = repository.findAllByOrderStatusAndCustomerId(status, customerId);
        for (OperationOrderEntity operationOrderEntity : response) {
            validator.validForView(operationOrderEntity);
        }
        return response.stream().map(this::buildResponse).toList();
    }

    public List<OperationOrderResponse> getListByStatus(OperationOrderStatusType status) {
        if (!SecurityContextAccessor.getRole().equals("ROLE_ADMIN") && !SecurityContextAccessor.getRole().equals("ROLE_MECHANIC")) {
            throw new ValidationException("You do not have permission");
        }
        return repository.findAllByOrderStatus(status).stream().map(this::buildResponse).toList();
    }

    @Override
    public Integer create(OperationOrderView view) {
        OperationOrderEntity entity = new OperationOrderEntity();
        merger.mergeCreate(entity, view);
        validator.validForCreate(entity);
        return repository.save(entity).getId();
    }

    @Override
    public boolean update(OperationOrderView view) {
        OperationOrderEntity entity = getById(view.getId());
        if (view.getEndDate() != null) {
            entity.setEndDate(view.getEndDate());
        }
        validator.validForUpdate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        OperationOrderEntity entity = getById(id);
        validator.validForDelete(entity);
        repository.delete(entity);
        return true;
    }

    @Override
    public boolean changeStatusOfOrder(Integer orderId, OperationOrderStatusType status) {
        OperationOrderEntity entity = getById(orderId);
        if (entity.getOrderStatus() != status) {
            entity.setOrderStatus(status);
            validator.validForUpdate(entity);
            repository.saveAndFlush(entity);
        }
        return true;
    }

    @Override
    public boolean operateOperation(Integer operationOrderId, Integer operationId, boolean isAdd) {
        OperationOrderEntity entity = getById(operationOrderId);
        Set<OperationEntity> operations = entity.getOperations();
        OperationEntity operation = operationRepository.findById(operationId).orElseThrow(
                () -> new NoSuchEntityException("Operation with id " + operationId + " not found")
        );
        if (isAdd) {
            operations.add(operation);
        } else {
            operations.remove(operation);
        }
        entity.setOperations(operations);
        validator.validForUpdate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean operateEmployee(Integer operationOrderId, Integer employeeId, boolean isAdd) {
        OperationOrderEntity entity = getById(operationOrderId);
        Set<EmployeeEntity> employees = entity.getEmployee();
        EmployeeEntity employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new NoSuchEntityException("Employee with id " + employeeId + " not found")
        );
        if (isAdd) {
            employees.add(employee);
        } else {
            employees.remove(employee);
        }
        entity.setEmployee(employees);
        validator.validForUpdate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    private OperationOrderResponse buildResponse(OperationOrderEntity entity) {
        return OperationOrderResponse.builder()
                .id(entity.getId())
                .orderStatus(entity.getOrderStatus())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .operations(entity.getOperations().stream()
                        .map(operationService::buildResponse)
                        .collect(Collectors.toSet())
                )
                .vehicle(vehicleService.buildResponse(entity.getVehicle()))
                .customer(customerService.buildResponse(entity.getCustomer()))
                .employee(entity.getEmployee().stream()
                        .map(operationService::buidEmployeeResponse)
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
