package com.example.crudboot.services;

import com.example.crudboot.dao.RoleDao;
import com.example.crudboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class RoleServiceImpl
        implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }

    @Override
    @Transactional
    public void deleteRole(int id) {
        roleDao.deleteRole(id);
    }

    @Override
    public Role getRole(int id) {
        return roleDao.getRole(id);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    @Override
    public Role getRole(String name) {
        return roleDao.getRole(name);
    }
}
