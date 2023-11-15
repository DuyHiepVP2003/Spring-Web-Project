package com.project.project.service;

import com.project.project.model.Order;
import com.project.project.model.OrderItem;
import com.project.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void save(Order order){
        orderRepository.save(order);
    }
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
}
