package com.pik.application.restController;

import com.pik.application.domain.User;
import com.pik.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
public class UserRestController {
	@Autowired
	private UserRepository userRepository;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> users() {
		return userRepository.findAll();
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> userById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		if (user.isEmpty()) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		} else if (user.get().getUsername().equalsIgnoreCase(loggedUsername)) {
			throw new RuntimeException("You cannot delete your account");
		} else {
			userRepository.delete(user.get());
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}

	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if (userRepository.findOneByUsername(user.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public User updateUser(@RequestBody User user) {
		if (userRepository.findOneByUsername(user.getUsername()) != null
				&& userRepository.findOneByUsername(user.getUsername()).getId() != user.getId()) {
			throw new RuntimeException("Username already exist");
		}
		return userRepository.save(user);
	}

}
