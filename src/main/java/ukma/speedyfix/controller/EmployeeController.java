package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.EmployeeCreationWrapper;
import ukma.speedyfix.domain.response.EmployeeResponse;
import ukma.speedyfix.service.employee.EmployeeService;
import ukma.speedyfix.service.user.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final UserService userService;

    @PostMapping("/admin/api/employee")
    public ResponseEntity<Integer> createEmployee(@RequestBody EmployeeCreationWrapper wrapper) {
        log.info("Creating employee: {}", wrapper.getEmployee());
        Integer userId = userService.create(wrapper.getUser());
        wrapper.getEmployee().setUserId(userId);
        return ResponseEntity.ok(employeeService.create(wrapper.getEmployee()));
    }

    @DeleteMapping("/admin/api/employee/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Integer id) {
        log.info("Deleting employee with id: {}", id);
        return ResponseEntity.ok(employeeService.delete(id));
    }

    @PutMapping("/admin/api/employee")
    public ResponseEntity<Boolean> updateEmployee(@RequestBody EmployeeCreationWrapper wrapper) {
        log.info("Updating employee: {}", wrapper.getEmployee());
        wrapper.getUser().setId(employeeService.getById(wrapper.getEmployee().getId()).getUser().getId());
        return ResponseEntity.ok(employeeService.update(wrapper.getEmployee()) && userService.update(wrapper.getUser()));
    }

    @GetMapping("/public/api/employee")
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        return ResponseEntity.ok(employeeService.getList());
    }

    @GetMapping("/public/api/employee/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(employeeService.getResponseById(id));
    }
}
