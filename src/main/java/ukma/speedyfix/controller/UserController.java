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
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final Marker USER_MARKER = MarkerManager.getMarker("UserController");
    private static final Marker UPDATE_MARKER = MarkerManager.getMarker("USER_UPDATE").setParents(USER_MARKER);
    private static final Marker CREATE_MARKER = MarkerManager.getMarker("USER_CREATE").setParents(USER_MARKER);

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserEntity user) {
        logger.info(CREATE_MARKER, "Creating user: {}", user);
        return ResponseEntity.ok(userService.create(user));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        logger.info("Deleting user with id: {}", id);
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAll() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        return ResponseEntity.ok(userService.getList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateUser(@RequestBody UserEntity user) {
        logger.info(UPDATE_MARKER,"Updating user: {}", user);
        return ResponseEntity.ok(userService.update(user));
    }
}
