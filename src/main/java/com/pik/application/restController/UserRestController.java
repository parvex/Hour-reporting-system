package com.pik.application.restController;

import com.pik.application.domain.User;
import com.pik.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/users")
	public List<User> users() {
		return userService.findAll();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> userById(@PathVariable Long id) {
		return userService.userById(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/users")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value="/supervisors")
	public List<User> getSupervisors(){
		return userService.findByRoles("Supervisor");
	}

	@PreAuthorize("hasAuthority('SUPERVISOR')")
	@GetMapping(value = "/available-employees")
	public List<User> getAvailableEmployees(@RequestParam(value="phrase") String phrase,
											@RequestParam List<Long> chosenId){

		return userService.getAvailableEmployees(phrase, chosenId);
	}
}
