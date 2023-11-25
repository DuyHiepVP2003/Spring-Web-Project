package com.project.project.controller;

import com.project.project.model.Category;
import com.project.project.model.Order;
import com.project.project.model.Product;
import com.project.project.model.User;
import com.project.project.service.CategoryService;
import com.project.project.service.OrderService;
import com.project.project.service.ProductService;
import com.project.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
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
    @Autowired
    private OrderService orderService;
    @GetMapping(path = "")
    public String getAdminPage(HttpSession session){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        return "redirect:/admin/category";
    }

    @GetMapping(path = "/category")
    public String manageCategory(Model model){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "adminsite/category/category";
    }
    @GetMapping(path = "/product")
    public String manageProduct(Model model){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "adminsite/product/product";
    }
    @GetMapping(path = "/user")
    public String manageUser(Model model){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "adminsite/user/user";
    }

    @GetMapping(path = "/order")
    public String manageOrder(Model model){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        List<Order> orders = orderService.getAllOrder();
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "adminsite/order/order";
    }
}
