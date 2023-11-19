package com.project.project.controller;

import com.project.project.model.Category;
import com.project.project.model.Product;
import com.project.project.service.CategoryService;
import com.project.project.service.ProductService;
import com.project.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @GetMapping(path = "/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model){
        Product product = productService.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "detail";
    }

    @GetMapping(path = "/admin/product/{id}")
    public String findProductById(@PathVariable Long id, Model model){
        Product product = productService.findById(id).orElse(null);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "adminsite/product/product-update";
    }

    @GetMapping(path = "/admin/product/addnew")
    public String addNewProduct(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "adminsite/product/product-addnew";
    }

    @PostMapping(path = "/admin/product/save")
    public String saveNewProduct(Product product){
        productService.save(product);
        return "redirect:/admin/product";
    }

    @RequestMapping(path = "/admin/product/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
        return "redirect:/admin/product";
    }

    @GetMapping(path = "/product/{id}")
    public String findProductByCategoryId(@PathVariable Long id, Model model){
        if (userService.isUserLogin()){
            model.addAttribute("isUserLogin", true);
        }
        else model.addAttribute("isUserLogin", false);
        List<Product> products = productService.findByCategoryId(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "home-product";
    }
}
