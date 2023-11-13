package com.project.project.service;

import com.project.project.model.User;
import com.project.project.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public void save(User user){
        userRepository.save(user);
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    public boolean isUserLogin(){
        User user = (User) session.getAttribute("user");
        if (user == null) return false;
        return true;
    }
}
