package ru.kata.spring.boot_security.demo.DataBase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;

    public DataInitializer(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ROLE_ADMIN");

        User admin = new User("admin", "admin", 35, "admin@mail.ru", "admin");

        User user = new User("user", "user", 30, "user@mail.ru", "user");
        admin.addRoleToUser(adminRole);


        userService.save(user);
        userService.save(admin);
    }
}
