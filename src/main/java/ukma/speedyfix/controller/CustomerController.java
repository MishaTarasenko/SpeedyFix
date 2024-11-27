package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.CustomerView;
import ukma.speedyfix.service.customer.CustomerService;
import ukma.speedyfix.service.user.UserService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;

    @PostMapping("/public/api/createCustomer")
    public ResponseEntity<Integer> createCustomer(@RequestBody UserEntity user) {
        log.info("Creating customer: {}", user);
        Integer userId = userService.create(user);
        return ResponseEntity.ok(customerService.create(new CustomerView(null, userId)));
    }

    @GetMapping("/admin/api/customer")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(customerService.getListResponse());
    }

    @GetMapping("/auth/api/customer/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(customerService.getResponseById(id));
    }

    @PutMapping("/auth/api/customer")
        public ResponseEntity<Boolean> updateCustomer(@RequestBody CustomerView user) {
        log.info("Updating customer: {}", user);
        return ResponseEntity.ok(customerService.update(user));
    }

    @DeleteMapping("/auth/api/customer/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable Integer id) {
        log.info("Deleting customer with id: {}", id);
        return ResponseEntity.ok(customerService.delete(id));
    }
}
