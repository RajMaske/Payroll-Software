package com.payroll.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.payroll.entity.User;
import com.payroll.repository.UserRepository;
import com.payroll.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private  UserRepository userRepository;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	

    

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        // Logic to fetch user details by userId (use UserRepository)
        return userRepository.findById(userId).orElse(null);
    }

    @GetMapping
    public List<User> getAllUsers() {
        // Logic to fetch all users (use UserRepository)
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // Logic to create a new user (use UserRepository)
        return customUserDetailsService.createUser(user.getUsername(),user.getPassword(),user.getRoles());
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        // Logic to update user details (use UserRepository)
        updatedUser.setId(userId); // Set the ID from the path variable
        return userRepository.save(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        // Logic to delete a user by userId (use UserRepository)
        userRepository.deleteById(userId);
    }
}
