package com.project.project.controller;

import com.project.project.model.Role;
import com.project.project.model.User;
import com.project.project.service.RoleService;
import com.project.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @PostMapping("/register")
    public String processRegistration(User user){
        if (userService.existsByUsername(user.getUsername())){
            return "redirect:/register?error=usernameExists";
        }
        if (userService.existsByEmail(user.getEmail())){
            return "redirect:/register?error=emailExists";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            return "redirect:/register?error=passwordNotMatch";
        }
        user.setName("");
        user.setPhoneNumber("");
        user.setAddress("");
        Role defaultRole = roleService.findById(2l).orElse(null);
        user.setRole(defaultRole);
        userService.save(user);
        return "redirect:/login";
    }
}
