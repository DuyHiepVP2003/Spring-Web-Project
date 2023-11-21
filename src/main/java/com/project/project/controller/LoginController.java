package com.project.project.controller;

import com.project.project.model.User;
import com.project.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @PostMapping(path = "/login")
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        User user = userService.findByUsername(username).orElse(null);
        if (user == null){
            return "redirect:/login?error=usernameNotExists";
        }
        if (!user.getPassword().equals(password)){
            return "redirect:/login?error=passwordWrong";
        }
        if (!user.isEnabled()){
            return "redirect:/login?error=userNotVerify";
        }
        session.setAttribute("user", user);
        return "redirect:/home";
    }

    @GetMapping(path = "/logout")
    public String processLogout(){
        session.removeAttribute("user");
        return "redirect:/home";
    }
}
