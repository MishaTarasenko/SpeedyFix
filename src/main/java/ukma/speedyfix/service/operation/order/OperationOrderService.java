package ukma.speedyfix.service.operation.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.entity.OperationOrderEntity;
import ukma.speedyfix.domain.type.OperationOrderStatusType;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.merger.OperationOrderMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.OperationOrderRepository;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OperationOrderService implements MyService<OperationOrderEntity, OperationOrderView, Integer>, MyOperationOrderService {

    private final MyValidator<OperationOrderEntity> validator;
    private final OperationOrderRepository repository;
    private final OperationRepository operationRepository;
    private final EmployeeRepository employeeRepository;
    private final OperationOrderMerger merger;

    @Override
    public OperationOrderEntity getById(Integer id) {
            return repository.findById(id).orElseThrow(
                    () -> new NoSuchEntityException("Operation order with id " + id + " not found")
            );
    }

    public List<OperationOrderEntity> getListByCustomerId(Integer customerId) {
        return repository.findAllByCustomerId(customerId);
    }

    public List<OperationOrderEntity> getListByEmployeeId(Integer employeeId) {
        return repository.findAllByEmployeeId(employeeId);
    }

    @Override
    public Integer create(OperationOrderView view) {
        OperationOrderEntity entity = new OperationOrderEntity();
        merger.merge(entity, view);
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
        return false;
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
}
