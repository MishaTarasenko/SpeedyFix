package ukma.speedyfix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.CustomerView;
import ukma.speedyfix.service.customer.CustomerService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/public/api/customer")
    public ResponseEntity<Integer> createCustomer(@RequestBody CustomerView customer) {
        log.info("Creating customer: {}", customer);
        return ResponseEntity.ok(customerService.create(customer));
    }

    @GetMapping("/public/api/customer")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(customerService.getListResponse());
    }

    @GetMapping("/public/api/customer/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(customerService.getResponseById(id));
    }

    @PutMapping("/user/api/customer")
        public ResponseEntity<Boolean> updateCustomer(@RequestBody CustomerView user) {
        log.info("Updating customer: {}", user);
        return ResponseEntity.ok(customerService.update(user));
    }

    @DeleteMapping("/user/api/customer/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable Integer id) {
        log.info("Deleting customer with id: {}", id);
        return ResponseEntity.ok(customerService.delete(id));
    }
}
