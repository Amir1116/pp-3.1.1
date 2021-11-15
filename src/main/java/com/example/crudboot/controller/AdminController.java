package com.example.crudboot.controller;

import com.example.crudboot.model.Role;
import com.example.crudboot.model.User;
import com.example.crudboot.services.RoleService;
import com.example.crudboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allusers")
    public String printUsers(ModelMap model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "allusers";
    }

    @GetMapping("/{username}")
    public String userPrivate(@PathVariable("username") String username, ModelMap model) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "adminprivate";
    }

    @GetMapping("/content")
    public String adminContent() {
        return "admincontent";
    }


    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, ModelMap model) {
        User user = userService.getUser(id);
        String role = null;
        model.addAttribute("user", user);
        model.addAttribute("role", role);
        return "adminedit";
    }

    @RequestMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @ModelAttribute("role") String role,
                         @PathVariable("id") int id) {
        User userOut = userService.getUser(id);
        userOut.setUsername(user.getUsername());
        userOut.setEmail(user.getEmail());
        userOut.setName(user.getName());
        userOut.setLastName(user.getLastName());
        userOut.setPassword(user.getPassword());
        System.out.println("RoOOOOLE = " + role);
        if (role.equals("on")) {
            Role admin = roleService.getRole("ADMIN");
            userOut.addRole(admin);
        }
        userService.updateUser(userOut);
        return "redirect:/admin/allusers";
    }

    @RequestMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/create")
    public String createPage(ModelMap model) {
        User user = new User();
        model.addAttribute(user);
        String admin = null;
        model.addAttribute("admin", admin);
        return "newuser";
    }

    @PostMapping("/new")
    public String newUserAdmin(@ModelAttribute("user") User user, @ModelAttribute("role") Role admin) {
        Role role = roleService.getRole("USER");
        role.addUserToRolen(user);
        user.addRole(role);
        user.setEnabled(1);
        if (admin != null) {
            Role adminin = roleService.getRole("ADMIN");
            user.addRole(adminin);
        }
        userService.save(user);
        return "redirect:/admin/allusers";
    }

}
