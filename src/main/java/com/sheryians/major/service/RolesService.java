package com.sheryians.major.service;

import com.sheryians.major.model.Roles;

public interface RolesService {
    Roles save(Roles roles);

    Roles findById(int id);
}
