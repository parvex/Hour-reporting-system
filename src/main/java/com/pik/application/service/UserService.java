package com.pik.application.service;

import com.pik.application.domain.User;
import com.pik.application.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneByUsername(String userName){
        return userRepository.findOneByUsername(userName);
    }

    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return findOneByUsername(loggedUsername);
    }

    public ResponseEntity<User> userById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<User> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (user.get().getUsername().equalsIgnoreCase(loggedUsername)) {
            throw new RuntimeException("You cannot delete your account");
        } else {
            userRepository.delete(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<User> createUser(User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exist");
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    public User updateUser(User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null
                && !userRepository.findOneByUsername(user.getUsername()).getId().equals(user.getId())) {
            throw new RuntimeException("Username already exist");
        }
        return userRepository.save(user);
    }

    public List<User> findByRoles(String role){
        return userRepository.findByRoles(role);
    }

    public List<User> getAvailableEmployees(String phrase, List<Long> chosenId) {
        Pageable page = PageRequest.of(0, 10);
        User loggedUser = getLoggedUser();
        return userRepository.findByUsernameLike(phrase, chosenId, loggedUser.getId(), page);
    }
}
