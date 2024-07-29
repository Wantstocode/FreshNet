package com.kiran.major.service;


import com.kiran.major.model.Roles;

public interface RolesService {
    Roles save(Roles roles);

    Roles findById(int id);
}
