package com.payroll.service;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payroll.entity.User;
import com.payroll.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRoles());
        return builder.build();
    }
    

    public User createUser(String username, String password, String	 roles) {
        // Check if username already exists
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists: " + username);
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(password);

        // Convert roles from array to set
        

        // Create user entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRoles(roles);

        // Save user to the database
        return userRepository.save(user);
    }
    public boolean authenticateUser(String username, String password) {
        // Retrieve user from the database by username
        User user = userRepository.findByUsername(username);

        // Check if user exists and password matches
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
