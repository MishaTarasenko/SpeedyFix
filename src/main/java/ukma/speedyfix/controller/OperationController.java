package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.OperationResponse;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.service.operation.OperationService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/admin/api/operation")
    public ResponseEntity<Integer> createOperation(@RequestBody OperationView view) {
        return ResponseEntity.ok(operationService.create(view));
    }

    @DeleteMapping(path = "/admin/api/operation/{id}")
    public ResponseEntity<Boolean> deleteOperation(@PathVariable Integer id) {
        log.info("Deleting operation with id: {}", id);
        return ResponseEntity.ok(operationService.delete(id));
    }

    @PutMapping("/admin/api/operation")
    public ResponseEntity<Boolean> updateOperation(@RequestBody OperationView operation) {
        log.info("Updating operation: {}", operation);
        return ResponseEntity.ok(operationService.update(operation));
    }

    @PutMapping(path = "/admin/api/operation/{id}/employee/add")
    public ResponseEntity<Boolean> addEmployees(@PathVariable("id") Integer id, @RequestParam List<Integer> employeesId) {
        log.info("Add employees to operation with id: {}", id);
        return ResponseEntity.ok(operationService.addRemoveEmployeesToOperation(id, employeesId, true));
    }

    @PutMapping(path = "/admin/api/operation/{id}/employee/remove")
    public ResponseEntity<Boolean> removeEmployees(@PathVariable("id") Integer id, @RequestParam List<Integer> employeesId) {
        log.info("Remove employees from operation with id: {}", id);
        return ResponseEntity.ok(operationService.addRemoveEmployeesToOperation(id, employeesId, false));
    }

    @GetMapping(path = "public/api/operation/{id}")
    public ResponseEntity<OperationResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(operationService.getResponseById(id));
    }

    @GetMapping("public/api/operation")
    public ResponseEntity<List<OperationResponse>> findAll() {
        return ResponseEntity.ok(operationService.getListResponse());
    }

    @GetMapping(path = "public/api/operation/employee/{id}")
    public ResponseEntity<List<OperationResponse>> findAllByEmployeeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(operationService.getAllOperationsByEmployeeId(id));
    }
}
