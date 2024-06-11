package com.sheryians.major.service;

import com.sheryians.major.model.Product;
import com.sheryians.major.model.Roles;
import com.sheryians.major.repository.RolesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService{

    @Autowired
    RolesRepository rolesRepository;

    @Transactional
    @Override
    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }

    @Override
    public Roles findById(int id) {
        Optional<Roles> result =rolesRepository.findById(id);
        Roles roles;

        if(result!=null) {
            roles = result.get();
            return roles;
        }
        else
            return null;
    }
}
