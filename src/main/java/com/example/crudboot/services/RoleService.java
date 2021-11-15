package com.example.crudboot.services;



import com.example.crudboot.model.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    List<Role> listRoles();

    void deleteRole(int id);

    Role getRole(int id);

    void updateRole(Role role);

    Role getRole(String name);
}
