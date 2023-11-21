package com.project.project.controller;

import com.project.project.model.Role;
import com.project.project.model.User;
import com.project.project.service.RoleService;
import com.project.project.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @PostMapping("/register")
    public String processRegistration(User user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if (userService.existsByUsername(user.getUsername())){
            return "redirect:/register?error=usernameExists";
        }
        if (userService.existsByEmail(user.getEmail())){
            return "redirect:/register?error=emailExists";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            return "redirect:/register?error=passwordNotMatch";
        }
        Role defaultRole = roleService.findById(2l).orElse(null);
        user.setRole(defaultRole);
        user.setEnabled(false);

        String randomString = RandomStringUtils.random(64, true, true);
        user.setVerificationCode(randomString);

        userService.save(user);

        String siteURL = request.getRequestURL().toString();
        userService.sendVerificationEmail(user, siteURL);
        return "successRegister";
    }

    @GetMapping("/register/verify")
    public String verifyAccount(@Param("code")String code){
        boolean isVerified = userService.verify(code);
        if (isVerified) return "successPage";
        return "404";
    }
}
