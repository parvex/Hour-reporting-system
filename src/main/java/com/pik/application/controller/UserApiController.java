package com.pik.application.controller;

import com.pik.application.domain.User;
import com.pik.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserApiController
{
    @Autowired
    UserRepository userRepository;


    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value = "id") Long id)
    {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    // Update a user
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
    {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException());

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
