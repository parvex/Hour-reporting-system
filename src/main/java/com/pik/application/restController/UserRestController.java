package com.pik.application.restController;

import com.pik.application.domain.User;
import com.pik.application.dto.EmployeeData.EmailNameSurProjectsPage;
import com.pik.application.dto.EmployeeData.IdUserNameSurEmailProjectsSupervisorRoles;
import com.pik.application.dto.EmployeeData.ListIdNameSurEmailSupervisor_NameTotal;
import com.pik.application.dto.LongString;
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
	public List<LongString> getSupervisors(String phrase){
		return userService.findSupervisorsByPhrase(phrase);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
	@PostMapping(value = "/available-employees")
	public ResponseEntity<List<LongString>> getAvailableEmployees(@RequestBody(required = false) PhraseList body){

		if(body != null)
			return userService.getAvailableEmployees(body.getPhrase(), body.getChosenIds(), body.getPage());
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
	@PostMapping(value = "/employees-list")
	public ResponseEntity<ListIdNameSurEmailSupervisor_NameTotal> getEmployeesPage(@RequestBody(required = false) EmailNameSurProjectsPage body){

		if(body != null)
			return userService.getEmployeesPage(body.getEmailNameSurProjects(), body.getPageOptions());
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
	@GetMapping(value = "/employees/{id}")
	public ResponseEntity<IdUserNameSurEmailProjectsSupervisorRoles> getEmployeeByID(@PathVariable Long id){
		return userService.getEmployeeById(id);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@GetMapping(value = "/employees")
	public ResponseEntity<List<IdUserNameSurEmailProjectsSupervisorRoles>> getAllEmployees(){
		return userService.findAllEmployees();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/employees")
	public ResponseEntity<IdUserNameSurEmailProjectsSupervisorRoles> updateUser(@RequestBody IdUserNameSurEmailProjectsSupervisorRoles body) {
		return userService.updateEmployee(body);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
	@GetMapping(value = "/unique-username")
	public ResponseEntity getEmployeeByID(@RequestParam String username){
		return userService.checkIfUsernameUnique(username);
	}
}
