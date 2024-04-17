package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findOne(int id);

    void save(User user);

    void update(User updatedPerson);

    void delete(int id);

    User findByName(String name);
}
