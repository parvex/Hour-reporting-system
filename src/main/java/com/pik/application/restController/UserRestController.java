package com.pik.application.restController;

import com.pik.application.domain.User;
import com.pik.application.dto.IdName;
import com.pik.application.dto.PhraseList;
import com.pik.application.service.UserService;
import org.springframework.http.HttpStatus;
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

//	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value="/findsupervisors")
	public List<IdName> getSupervisors(String phrase){
		return userService.findSupervisorsByPhrase(phrase);
	}

	@PreAuthorize("hasAuthority('SUPERVISOR')")
	@PostMapping(value = "/available-employees")
	public ResponseEntity<List<IdName>> getAvailableEmployees(@RequestBody(required = false) PhraseList body){

		if(body != null)
			return userService.getAvailableEmployees(body.getPhrase(), body.getChosenIds());
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
