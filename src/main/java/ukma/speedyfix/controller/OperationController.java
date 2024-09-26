package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.OperationResponse;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.service.operation.OperationService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/operation")
public class OperationController {

    private final OperationService operationService;

    @PostMapping
    public ResponseEntity<Integer> createOperation(@RequestBody OperationView view) {
        return ResponseEntity.ok(operationService.create(view));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteOperation(@PathVariable Integer id) {
        log.info("Deleting operation with id: {}", id);
        return ResponseEntity.ok(operationService.delete(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateOperation(@RequestBody OperationView operation) {
        log.info("Updating operation: {}", operation);
        return ResponseEntity.ok(operationService.update(operation));
    }

    @PutMapping(path = "/{id}/employee/add")
    public ResponseEntity<Boolean> addEmployees(@PathVariable("id") Integer id, @RequestBody List<Integer> employeesId) {
        log.info("Add employees to operation with id: {}", id);
        return ResponseEntity.ok(operationService.addRemoveEmployeesToOperation(id, employeesId, true));
    }

    @PutMapping(path = "/{id}/employee/remove")
    public ResponseEntity<Boolean> removeEmployees(@PathVariable("id") Integer id, @RequestParam List<Integer> employeesId) {
        log.info("Remove employees from operation with id: {}", id);
        return ResponseEntity.ok(operationService.addRemoveEmployeesToOperation(id, employeesId, false));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OperationResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(operationService.getResponseById(id));
    }

    @GetMapping
    public ResponseEntity<List<OperationResponse>> findAll() {
        return ResponseEntity.ok(operationService.getListResponse());
    }

}
