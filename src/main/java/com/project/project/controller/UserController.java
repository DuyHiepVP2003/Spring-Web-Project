package com.project.project.controller;

import com.project.project.model.Role;
import com.project.project.model.User;
import com.project.project.service.RoleService;
import com.project.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/admin/user/addnew")
    public String addNewUser(Model model){
        List<Role> roles = roleService.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "user-addnew";
    }

    @PostMapping(path = "/admin/user/save")
    public String saveNewUser(User user){
        userService.save(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(path = "/admin/user/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteUserById(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:/admin/user";
    }

    @GetMapping(path = "/admin/user/update/{id}")
    public String findUserById(@PathVariable Long id, Model model){
        User user = userService.findById(id).orElse(null);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user-update";
    }


}
