package com.project.project.controller;

import com.project.project.model.*;
import com.project.project.service.OrderItemService;
import com.project.project.service.OrderService;
import com.project.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @PostMapping(path = "/checkout")
    public String submitOrder(@ModelAttribute("orderForm") OrderFormDTO orderForm, HttpSession session){
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null){
            cart = new ShoppingCart();
        }
        Order order = new Order();
        order.setCustomerName(orderForm.getFname()+" "+orderForm.getLname());
        order.setCustomerAddress(orderForm.getAddress());
        order.setCheckOut(false);
        order.setCustomerEmail(orderForm.getEmail());
        order.setCustomerPhone(orderForm.getPhone());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem:cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotal(cart.calculateTotal());
        User user = (User) session.getAttribute("user");
        order.setCreatedBy(user.getUsername());
        orderService.save(order);
        session.removeAttribute("cart");
        return "redirect:/home";
    }

    @GetMapping(path = "/admin/order/{id}")
    public String orderDetail(@PathVariable Long id, Model model){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        Order order = orderService.getOrderById(id).orElse(null);
        model.addAttribute("order",order);
        return "adminsite/order/order-detail";
    }

    @RequestMapping(path = "/admin/order/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteOrder(@PathVariable Long id){
        if (!userService.isUserLogin()) {
            return "redirect:/login";
        }
        if (!userService.isUserRoleIsAdmin()){
            return "redirect:/page_not_found";
        }
        orderService.deleteOrderById(id);
        return "redirect:/admin/order";
    }
}
