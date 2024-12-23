package com.betaplan.anjeza.auth.services;

import com.betaplan.anjeza.auth.enums.Role;
import com.betaplan.anjeza.auth.models.User;
import com.betaplan.anjeza.auth.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Adds a new user with the specified role.
     *
     * @param user the User to be added. User must not contain a non-null password.
     * @param role the Role to assign to the user
     */
    public void addUser(User user, Role role) {
        // Encrypt the user's password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // Assign the specified role to the user
        user.setRole(role);
        // Persist the user entity in the repository
        userRepository.save(user);
    }

    /**
     * Retrieve a user based on its username
     *
     * @param username the username of the user to retrieve
     * @return the User entity associated with the given username, or null if no user with the specified username exists
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}