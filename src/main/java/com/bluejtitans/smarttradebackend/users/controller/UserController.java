package com.bluejtitans.smarttradebackend.users.controller;

<<<<<<< HEAD
import com.bluejtitans.smarttradebackend.users.controller.http.login.LoginRequest;
import com.bluejtitans.smarttradebackend.users.controller.http.login.LoginResponse;
import com.bluejtitans.smarttradebackend.users.controller.http.register.RegisterResponse;
=======
import com.bluejtitans.smarttradebackend.lists.model.ProductList;
import com.bluejtitans.smarttradebackend.lists.service.ListService;
import com.bluejtitans.smarttradebackend.users.http.login.LoginCredentials;
import com.bluejtitans.smarttradebackend.users.http.login.LoginRequest;
import com.bluejtitans.smarttradebackend.users.http.login.LoginResponse;
import com.bluejtitans.smarttradebackend.users.http.register.RegisterResponse;
import com.bluejtitans.smarttradebackend.users.model.Client;
>>>>>>> dcb5af11a9c026734d6ebec933b62f8465d96d00
import com.bluejtitans.smarttradebackend.users.model.User;
import com.bluejtitans.smarttradebackend.users.model.UserFactory;
import com.bluejtitans.smarttradebackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userType}")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody String userJson, @PathVariable String userType) {
        userType = userType.substring(0, userType.length() - 1);
        User user = (User) UserFactory.createUserFromJson(userType, userJson);

        return userService.saveUser(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }
}