package com.example.crudboot.services;

import com.example.crudboot.dao.UserDao;
import com.example.crudboot.model.Role;
import com.example.crudboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserServiceImpl
        implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, String role, int id) {
        User userOut = getUser(id);
        userOut.setUsername(user.getUsername());
        userOut.setEmail(user.getEmail());
        userOut.setName(user.getName());
        userOut.setLastName(user.getLastName());
        userOut.setPassword(user.getPassword());
        if (role.equals("on")) {
            Role admin = roleService.getRole("ADMIN");
            userOut.addRole(admin);
        }
        userDao.updateUser(userOut);
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }
}
