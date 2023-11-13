package com.project.project.controller;

import com.project.project.model.Category;
import com.project.project.model.Product;
import com.project.project.model.User;
import com.project.project.service.CategoryService;
import com.project.project.service.ProductService;
import com.project.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @GetMapping(path = "")
    public String getHomePath(Model model){
        return "redirect:/home";
    }

    @GetMapping(path = "home")
    public String getHome(Model model){
        if (userService.isUserLogin()){
            model.addAttribute("isUserLogin", true);
        }
        else model.addAttribute("isUserLogin", false);
        List<Category> categories = categoryService.findAll();
        List<Product> products = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping(path = "login")
    public String loginPage(@RequestParam(name = "error", required = false)String error, Model model){
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping(path = "register")
    public String registerPage(@RequestParam(name = "error", required = false)String error, Model model){
        model.addAttribute("user", new User());
        model.addAttribute("error", error);
        return "register";
    }

    @GetMapping(path = "checkout")
    public String getCheckOut(Model model){
        if (userService.isUserLogin()){
            model.addAttribute("isUserLogin", true);
        }
        else model.addAttribute("isUserLogin", false);
        return "checkout";
    }

    @GetMapping(path = "/product")
    public String getProductPage(Model model){
        if (userService.isUserLogin()){
            model.addAttribute("isUserLogin", true);
        }
        else model.addAttribute("isUserLogin", false);
        List<Category> categories = categoryService.findAll();
        List<Product> products = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "home-product";
    }
}
