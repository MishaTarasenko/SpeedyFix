package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.OperationOrderResponse;
import ukma.speedyfix.domain.type.OperationOrderStatusType;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.service.operation.order.OperationOrderService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OperationOrderController {
    
    private final OperationOrderService service;

    @PostMapping("/user/api/operation/order")
    public ResponseEntity<Integer> createOperationOrder(@RequestBody OperationOrderView view) {
        log.info("Create operation order: {}", view);
        return ResponseEntity.ok(service.create(view));
    }

    @DeleteMapping(path = "/auth/api/operation/order/{id}")
    public ResponseEntity<Boolean> deleteOperation(@PathVariable Integer id) {
        log.info("Deleting operation order with id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping("/auth/api/operation/order")
    public ResponseEntity<Boolean> updateOperation(@RequestBody OperationOrderView operation) {
        log.info("Updating operation order: {}", operation);
        return ResponseEntity.ok(service.update(operation));
    }

    @GetMapping(path = "/auth/api/operation/order/{id}")
    public ResponseEntity<OperationOrderResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getResponseById(id));
    }

    @GetMapping(path = "/auth/api/operation/order/customer/{id}")
    public ResponseEntity<List<OperationOrderResponse>> findAllByCustomerId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getListByCustomerId(id));
    }

    @GetMapping(path = "/admin/api/operation/order/employee/{id}")
    public ResponseEntity<List<OperationOrderResponse>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getListByEmployeeId(id));
    }

    @GetMapping(path = "/auth/api/operation/order/{status}/customer/{id}")
    public ResponseEntity<List<OperationOrderResponse>> findAllByStatusAndCustomerId(@PathVariable("status")OperationOrderStatusType status, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getListByStatusAndCustomerId(status, id));
    }

    @GetMapping(path = "/admin/api/operation/order/{status}")
    public ResponseEntity<List<OperationOrderResponse>> findAllByStatus(@PathVariable("status")OperationOrderStatusType status) {
        return ResponseEntity.ok(service.getListByStatus(status));
    }

    @PutMapping(path = "/auth/api/operation/order/{id}/new/{status}")
    public ResponseEntity<Boolean> changeStatusOfOperation(@PathVariable Integer id, @PathVariable OperationOrderStatusType status) {
        return ResponseEntity.ok(service.changeStatusOfOrder(id, status));
    }
}
