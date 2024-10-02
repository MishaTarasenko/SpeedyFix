package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.EmployeeResponse;
import ukma.speedyfix.domain.view.EmployeeView;
import ukma.speedyfix.service.employee.EmployeeService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Integer> createEmployee(@RequestBody EmployeeView employee) {
        log.info("Creating employee: {}", employee);
        return ResponseEntity.ok(employeeService.create(employee));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Integer id) {
        log.info("Deleting employee with id: {}", id);
        return ResponseEntity.ok(employeeService.delete(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateEmployee(@RequestBody EmployeeView employee) {
        log.info("Updating employee: {}", employee);
        return ResponseEntity.ok(employeeService.update(employee));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        return ResponseEntity.ok(employeeService.getList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(employeeService.getResponseById(id));
    }
}
