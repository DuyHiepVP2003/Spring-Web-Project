package com.project.project.controller;

import com.project.project.model.User;
import com.project.project.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

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

    @GetMapping(path = "/resetPassword")
    public String resetPassword(@RequestParam(name = "error", required = false)String error, Model model){
        model.addAttribute("isUserLogin", false);
        model.addAttribute("error", error);
        return "reset-password";
    }

    @PostMapping(path = "/resetPasswordRequest")
    public String sendResetPasswordEmail(@RequestParam("email") String email, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User user = userService.findByEmail(email).orElse(null);
        if (user == null){
            return "redirect:/resetPassword?error=emailNotExist";
        }

        String randomString = RandomStringUtils.random(64, true, true);
        user.setVerificationCode(randomString);
        userService.save(user);
        String siteURL = request.getRequestURL().toString();
        userService.sendResetPasswordEmail(user, siteURL);
        return "successRegister";
    }

    @GetMapping("/resetPasswordRequest/verify")
    public String verifyResetPassword(@Param("code")String code, Model model){
        User user = userService.findByVerificationCode(code).orElse(null);
        if (user==null) return "404";
        model.addAttribute("isUserLogin", false);
        return "get-new-password";
    }

    @PostMapping("/resetPasswordRequest/verify")
    public String confirmResetPassword(@Param("code")String code, @RequestParam("password1") String password1, @RequestParam("password2") String password2, Model model, HttpServletRequest request){
        User user = userService.findByVerificationCode(code).orElse(null);
        if (user==null) return "404";
        user.setPassword(password1);
        userService.save(user);
        return "successRegister";
    }
}
