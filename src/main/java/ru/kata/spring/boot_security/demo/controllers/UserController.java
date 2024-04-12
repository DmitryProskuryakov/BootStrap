package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userServiceImpl;
    private final RoleService roleServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, RoleService roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String getAllUsers(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("user", userServiceImpl.findOne(id));
            return "admin";
        } else {
            model.addAttribute("roles", roleServiceImpl.getRoleSet());
            model.addAttribute("users", userServiceImpl.findAll());
            return "admin";
        }
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id", required = false) Integer id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }

    @PostMapping()
    public String addNewUser(@ModelAttribute("user") User user) {
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @PatchMapping()
    public String editUser(@ModelAttribute("user") User user, @ModelAttribute("roles") Set<Role> roleSet, @RequestParam(value = "id", required = false) Integer id) {
        user.setListRoles(roleSet);
        userServiceImpl.update(id, user);
        return "redirect:/admin";
    }
}
