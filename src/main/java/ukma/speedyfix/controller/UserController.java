package ukma.speedyfix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.user.UserService;
import org.springframework.ui.Model;


import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/public/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final Marker USER_MARKER = MarkerManager.getMarker("UserController");
    private static final Marker UPDATE_MARKER = MarkerManager.getMarker("USER_UPDATE").setParents(USER_MARKER);
    private static final Marker CREATE_MARKER = MarkerManager.getMarker("USER_CREATE").setParents(USER_MARKER);

    private final UserService userService;

//    @PostMapping("/public/api/user")
//    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserEntity user) {
//        return ResponseEntity.ok(userService.create(user));
//    }
    @PostMapping(path = "/public/api/createUser")
    public String createUser(@ModelAttribute("user") @Valid UserEntity user) {
        logger.info(CREATE_MARKER, "Creating user: {}", user);
        userService.create(user);
        return "redirect:/public/loginPage";
    }

    @DeleteMapping (path = "/admin/deleteUser/{id}")
        public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        logger.info("Deleting user with id: {}", id);
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("auth/api/user")
    public String findAll(Model model) {
        logger.info("Getting all users");
        List<UserEntity> usersList = userService.getList();
        model.addAttribute("listUsers", usersList);
        String role = userService.getUserRole(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(role);
        model.addAttribute("userRole", role);
        return "index";
    }


//    @GetMapping(path = "/{id}")
//    public ResponseEntity<UserEntity> findById(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(userService.getById(id));
//    }

//    @PostMapping(path = "updateUser")
//    public String updateUser(@ModelAttribute("user") UserEntity user) {
//        logger.info("Updating user: {}", user);
//        userService.update(user);
//        return "redirect:/api/user";
//    }

//    @GetMapping(path = "formForUpdateUser/{id}")
//    public String formForUpdateUser(@PathVariable("id") Integer id, Model model) {
//        UserEntity user = userService.getById(id);
//        logger.info("user: {}", user);
//        model.addAttribute("user", user);
//        return "update_employee";
//    }

    @GetMapping(path = "public/api/createUserForm")
    public String formForCreateUser(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        return "create_employee";
    }
}
