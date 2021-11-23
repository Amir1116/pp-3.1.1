package com.example.crudboot.dao;


import com.example.crudboot.model.Role;

import java.util.List;

public interface RoleDao {
    void save(Role role);

    List<Role> getRolesList();

    void deleteRole(int id);

    Role getRole(int id);

    void updateRole(Role role);

    Role getRole(String name);
}
