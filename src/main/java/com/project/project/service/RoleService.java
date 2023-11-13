package com.project.project.service;

import com.project.project.model.Role;
import com.project.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
    public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
    }
}
