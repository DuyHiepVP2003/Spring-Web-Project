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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminSiteController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    @GetMapping(path = "")
    public String getAdminPage(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "admin";
    }

    @GetMapping(path = "/category")
    public String manageCategory(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category";
    }
    @GetMapping(path = "/product")
    public String manageProduct(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product";
    }
    @GetMapping(path = "/user")
    public String manageUser(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user";
    }
}
