package com.project.project.service;

import com.project.project.model.Order;
import com.project.project.model.OrderItem;
import com.project.project.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }
    public void deleteOrderById(Long id){
        orderRepository.deleteById(id);
    }
}
