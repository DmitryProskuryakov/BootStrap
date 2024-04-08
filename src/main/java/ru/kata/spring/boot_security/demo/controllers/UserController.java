package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public String getAllUsers(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("user", userServiceImpl.findOne(id));
            return "admin";
        } else {
            model.addAttribute("users", userServiceImpl.findAll());
            return "admin";
        }
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(value = "id", required = false) Integer id) {
        userServiceImpl.delete(id);
        return "redirect:/users/get-all";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String addNewUser(@ModelAttribute("user") User user) {
        userServiceImpl.save(user);
        return "redirect:/users/get-all";
    }

    @GetMapping("/change")
    public String changeUser(@ModelAttribute("user") User user) {
        return "change";
    }

    @PatchMapping()
    public String editUser(@ModelAttribute("user") User user, @RequestParam(value = "id", required = false) Integer id) {
        userServiceImpl.update(id, user);
        return "redirect:/users/get-all";
    }
}
