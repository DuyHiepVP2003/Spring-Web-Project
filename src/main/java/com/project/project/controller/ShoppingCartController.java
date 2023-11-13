package com.project.project.controller;

import com.project.project.model.CartItem;
import com.project.project.model.Product;
import com.project.project.model.ShoppingCart;
import com.project.project.service.ProductService;
import com.project.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingCartController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/cart")
    public String getCartPage(Model model, HttpSession session){
        if (userService.isUserLogin()){
            model.addAttribute("isUserLogin", true);
        }
        else model.addAttribute("isUserLogin", false);
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null){
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam Long productId,@RequestParam int quantity, HttpSession session){
        Product product = productService.findById(productId).orElse(null);
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null){
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        CartItem cartItem = new CartItem(product, quantity);
        cart.addCartItem(cartItem);
        return "redirect:/cart";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeCartItem(productId);
        }
        return "redirect:/cart";
    }

    @GetMapping("/removeItem")
    public String removeItem(@RequestParam Long productId, HttpSession session){
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null){
            cart.removeCartButton(productId);
        }
        return "redirect:/cart";
    }
}
