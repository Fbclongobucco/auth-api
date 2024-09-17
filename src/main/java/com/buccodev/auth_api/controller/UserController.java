package com.buccodev.auth_api.controller;

import com.buccodev.auth_api.dto.UserDTO;
import com.buccodev.auth_api.repositories.UserRepository;
import com.buccodev.auth_api.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO saveUser(@RequestBody UserDTO userDTO){

        return userService.save(userDTO);
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "permissão administrador";
    }

    @GetMapping("/user")
    public String getUser(){
        return "permissão de usuário";
    }

}
