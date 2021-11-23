package com.example.crudboot.controller;

import com.example.crudboot.model.User;
import com.example.crudboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String userPrivate(@PathVariable("username") String username, ModelMap model) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "userprivate";
    }

    @GetMapping("/content")
    public String userShow() {
        return "usercontent";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, ModelMap model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "useredit";
    }

    @RequestMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @ModelAttribute("role") String role,
                         @PathVariable("id") int id, ModelMap model) {
        userService.updateUser(user,role,id);
        User userOut = userService.getUser(id);
        model.addAttribute("user", userOut);
        if (userOut.isAdmin()) {
            return "redirect:/admin/" + userOut.getUsername();
        } else {
            return "redirect:/user/" + userOut.getUsername();
        }
    }

}
