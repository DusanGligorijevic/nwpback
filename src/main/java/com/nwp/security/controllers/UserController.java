package com.nwp.security.controllers;

import com.nwp.security.services.UserService;
import com.nwp.security.user.Role;
import com.nwp.security.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Data
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        System.out.println("uslo");
        return userService.getAll();
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.save(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id")Long id){
        userService.deleteById(id);
    }

    @GetMapping("role/{id}")
    public Role getRole(@PathVariable(name = "id") Long id){
        System.out.println("uslo");
        return userService.getRole(id);
    }
}
