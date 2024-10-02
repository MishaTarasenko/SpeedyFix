package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.OperationOrderResponse;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.service.operation.order.OperationOrderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/operation/order")
@RequiredArgsConstructor
public class OperationOrderController {
    
    private final OperationOrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOperationOrder(@RequestBody OperationOrderView view) {
        System.out.println(view);
        log.info("Create operation order: {}", view);
        return ResponseEntity.ok(service.create(view));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteOperation(@PathVariable Integer id) {
        log.info("Deleting operation order with id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateOperation(@RequestBody OperationOrderView operation) {
        log.info("Updating operation order: {}", operation);
        return ResponseEntity.ok(service.update(operation));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OperationOrderResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getResponseById(id));
    }

    @GetMapping(path = "/customer/{id}")
    public ResponseEntity<List<OperationOrderResponse>> findAllByCustomerId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getListByCustomerId(id));
    }

    @GetMapping(path = "/employee/{id}")
    public ResponseEntity<List<OperationOrderResponse>> findAllByEmployeeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getListByEmployeeId(id));
    }

    @PutMapping(path = "{orderId}/operation/{operationId}/{isAdd}")
    public ResponseEntity<Boolean> operateOperation(@PathVariable Integer orderId, @PathVariable Integer operationId, @PathVariable boolean isAdd) {
        return ResponseEntity.ok(service.operateOperation(orderId, operationId, isAdd));
    }

    @PutMapping(path = "{orderId}/employee/{employeeId}/{isAdd}")
    public ResponseEntity<Boolean> operateEmployee(@PathVariable Integer orderId, @PathVariable Integer employeeId, @PathVariable boolean isAdd) {
        return ResponseEntity.ok(service.operateEmployee(orderId, employeeId, isAdd));
    }
}
