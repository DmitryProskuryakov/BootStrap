package ru.kata.spring.boot_security.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOne(int id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        User userFromDb = userRepository.findByFirstName(user.getFirstName());

        if (userFromDb != null) {
            return;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User updatedPerson) {
        User userFromDb = userRepository.findByFirstName(updatedPerson.getFirstName());

        if (userFromDb == null) {
            return;
        }

        userFromDb.setFirstName(updatedPerson.getFirstName());
        userFromDb.setLastName(updatedPerson.getLastName());
        userFromDb.setEmail(updatedPerson.getEmail());
        userFromDb.setPassword(bCryptPasswordEncoder.encode(updatedPerson.getPassword()));
        userFromDb.getListRoles().clear();

        for (Role role : updatedPerson.getListRoles()) {
            userFromDb.addRoleToUser(role);
        }

        userRepository.save(userFromDb);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByFirstName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByFirstName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User has not found!");
        }
        return user;
    }
}
