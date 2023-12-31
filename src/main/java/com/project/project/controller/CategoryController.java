package com.project.project.controller;

import com.project.project.model.Category;
import com.project.project.service.CategoryService;
import com.project.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/admin/category/addnew")
    public String addNewCategory(){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }return "adminsite/category/category-addnew";
    }
    @PostMapping(path = "/admin/category/save")
    public String postNewCategory(Category category){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping(path = "/admin/category/{id}")
    public String findCategoryById(@PathVariable Long id, Model model){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        Category category = categoryService.findCategoryById(id).orElse(null);
        model.addAttribute("category", category);
        return "adminsite/category/category-update";
    }
    @RequestMapping(path = "admin/category/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteById(@PathVariable Long id){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }
}
