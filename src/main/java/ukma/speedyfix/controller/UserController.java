package ukma.speedyfix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.user.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/public/api/user")
    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserEntity user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @DeleteMapping("/public/api/user/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/public/api/user")
    public ResponseEntity<List<UserEntity>> findAll() {
        return ResponseEntity.ok(userService.getList());
    }

    @GetMapping("/public/api/user/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/auth/api/user")
    public ResponseEntity<Boolean> updateUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.update(user));
    }
}
