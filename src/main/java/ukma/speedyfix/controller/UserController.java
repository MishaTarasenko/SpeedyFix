package ukma.speedyfix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.user.UserService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserEntity user) {
        log.info("Creating user: {}", user);
        return ResponseEntity.ok(userService.create(user));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        log.info("Deleting user with id: {}", id);
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAll() {
        return ResponseEntity.ok(userService.getList(null));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateUser(@RequestBody UserEntity user) {
        log.info("Updating user: {}", user);
        return ResponseEntity.ok(userService.update(user));
    }
}
