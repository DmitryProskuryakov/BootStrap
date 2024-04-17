package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userServiceImpl;
    private final RoleService roleServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleService roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String getAllUsers(Principal principal, Model model) {
        model.addAttribute("roles", roleServiceImpl.getRoleSet());
        model.addAttribute("users", userServiceImpl.findAll());
        model.addAttribute("user", userServiceImpl.findByName(principal.getName()));

        return "admin";
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
    public String editUser(@ModelAttribute("user") User user, @ModelAttribute("roles") Set<Role> roleSet) {
        user.setListRoles(roleSet);
        userServiceImpl.update(user);

        return "redirect:/admin";
    }
}