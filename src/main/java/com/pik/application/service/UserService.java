package com.pik.application.service;

import com.pik.application.domain.Project;
import com.pik.application.domain.SystemRole;
import com.pik.application.domain.User;
import com.pik.application.dto.EmployeeData.*;
import com.pik.application.dto.IdBoolOrderPage;
import com.pik.application.dto.LongString;
import com.pik.application.dto.PageOptions;
import com.pik.application.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProjectService projectService;

    public UserService(UserRepository userRepository, @Lazy ProjectService projectService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
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

    public List<LongString> findSupervisorsByPhrase(String phrase){
        Pageable page = PageRequest.of(0, 10);
        return userRepository.findSupervisorsByUsernameLike(phrase, page);
    }

    public ResponseEntity<List<LongString>> getAvailableEmployees(String phrase, List<Long> chosenIds, Pageable page) {
        Long loggedId = getLoggedUser().getId();
        if(chosenIds != null && chosenIds.isEmpty())
            chosenIds.add(-1L);
        List<LongString> body = userRepository.findByUsernameLike(phrase, chosenIds, loggedId, page);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<ListIdNameSurEmailSupervisor_NameTotal> getEmployeesPage(EmailNameSurProjects employee, PageOptions pageOptions) {
        Pageable page = PageRequest.of(pageOptions.getPage(), pageOptions.getCount());

        if(employee.getProjects() != null && employee.getProjects().isEmpty())
            employee.getProjects().add(-1L);

        Long loggedId = getLoggedUser().getId();
        List<IdNameSurEmailSupervisor_NameProjects> body = userRepository.findByIdNameSurEmailSupervisorPage(employee.getEmail(),
                employee.getName(), employee.getSurname(), employee.getProjects(), loggedId, page);

        // Again call query to get TOTAL COUNT
        List<IdNameSurEmailSupervisor_NameProjects> bodyMax = userRepository.findByIdNameSurEmailSupervisorPage(employee.getEmail(),
                employee.getName(), employee.getSurname(), employee.getProjects(), loggedId, null);

        // Add projects for every returned employee in {id, name} form
        for(IdNameSurEmailSupervisor_NameProjects user : body){
            List<LongString> projects = projectService.findProjectsForUser(user.getId());
            user.setProjects(projects);
        }

        ListIdNameSurEmailSupervisor_NameTotal bodyTotal = new ListIdNameSurEmailSupervisor_NameTotal(body, bodyMax.size());
        return new ResponseEntity<>(bodyTotal, HttpStatus.OK);
    }

    public ResponseEntity<IdUserNameSurEmailProjectsSupervisorRoles> getEmployeeById(Long id) {
        Long loggedId = getLoggedUser().getId();
        Optional<IdUserNameSurEmailProjectsSupervisorRoles> body = userRepository.getEmployeeById(id, loggedId);
        if(body.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else {
            List<String> roles = userRepository.findAllRolesForUser(id);
            List<LongString> rolesNum = new ArrayList<>();
            long num = 1L;
            for(String r : roles){
               rolesNum.add(new LongString(num++, r));
            }
            body.get().setRoles(rolesNum);

            List<LongString> projects = projectService.findProjectsForUser(id);
            body.get().setProjects(projects);

            return new ResponseEntity<>(body.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<List<IdUserNameSurEmailProjectsSupervisorRoles>> findAllEmployees() {
        List<IdUserNameSurEmailProjectsSupervisorRoles> employees = userRepository.findAllEmployees();

        for(IdUserNameSurEmailProjectsSupervisorRoles employee : employees){
            List<String> roles = findAllRolesForUser(employee.getId());
            List<LongString> rolesNum = new ArrayList<>();
            for (String r : roles) {
                if(r.equals(SystemRole.ADMIN.toString()))
                    rolesNum.add(new LongString(0L, r));
                else if(r.equals(SystemRole.SUPERVISOR.toString()))
                    rolesNum.add(new LongString(2L, r));
                else
                    rolesNum.add(new LongString(1L, r));
            }
            employee.setRoles(rolesNum);
            List<LongString> projects = projectService.findProjectsForUser(employee.getId());
            employee.setProjects(projects);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    public List<String> findAllRolesForUser(Long id){
        return userRepository.findAllRolesForUser(id);
    }

    public Boolean checkIfHasRole(Long id, String role){
        List<String> roles = findAllRolesForUser(id);
        return roles.contains(role);
    }

    public ResponseEntity<IdUserNameSurEmailProjectsSupervisorRoles> addNewEmployee(IdUserNameSurEmailProjectsSupervisorRoles body) {

        User user;
        if(body.getId() == null)
        {
            user = new User();
            Optional<User> supervisor = userRepository.findById(body.getSupervisorId());
            if(supervisor.isEmpty() || !checkIfHasRole(supervisor.get().getId(), "SUPERVISOR")// change only if selected user is a supervisor
            || body.getName() == null || body.getSurname() == null || body.getUsername() == null || body.getEmail() == null || body.getPassword() == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                user.setSupervisor(supervisor.get());

            List<String> newRoles = new ArrayList<>();
            if(body.getRoles() == null)
                newRoles.add("USER");
            else {
                for (LongString role : body.getRoles()) {
                    newRoles.add(role.getName()); // returns 500 if projects don't exist
                }
            }
            user.setRoles(newRoles);
        } else {
                Optional<User> findUser = userRepository.findById(body.getId());
                user = findUser.get();
                if (!body.getSupervisorId().equals(user.getSupervisor().getId())) {
                    Optional<User> supervisor = userRepository.findById(body.getSupervisorId());
                    if (checkIfHasRole(supervisor.get().getId(), "SUPERVISOR")) // change only if selected user is a supervisor
                     user.setSupervisor(supervisor.get());
                }
        }
        user.setName(body.getName());
        user.setSurname(body.getSurname());
        user.setUsername(body.getUsername());
        user.setEmail(body.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(body.getPassword()));

        Set<Project> newProjects = new HashSet<>();
        for (LongString project : body.getProjects()) {
            newProjects.add(projectService.findById(project.getId())); // returns 500 if projects don't exist
        }
        user.setProjects(newProjects);
        userRepository.save(user); // update or save user
        return getEmployeeById(user.getId());
    }

    public ResponseEntity checkIfUsernameUnique(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity checkIfEmailUnique(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty())
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public User findById(Long employeeId) {
        Optional<User> employee = userRepository.findById(employeeId);
        if(employee.isEmpty())
            throw new RuntimeException("Employee not found");
        return employee.get();
    }

    public void updateEmployeeProjects(User employee) {
        userRepository.save(employee);
    }

    public List<Long> findEmployeeIdsForProject(Long projectId){
        Optional<List<Long>> employees = userRepository.findEmployeesForProject(projectId);
        if(employees.isEmpty()) return null;
        else return employees.get();
    }

    public ResponseEntity<ListIdNameSurUserEmailTotal> findEmployeesAssigned(Long projectId, PageOptions options, String order) {
        Long loggedId = getLoggedUser().getId();

        Pageable page = order.isBlank() ? PageRequest.of(options.getPage(), options.getCount())
                : order.equals("ASC") ? PageRequest.of(options.getPage(), options.getCount(), Sort.Direction.ASC, "name")
                : PageRequest.of(options.getPage(), options.getCount(), Sort.Direction.DESC, "name");

        List<IdNameSurUserEmail> employees = userRepository.findEmployeesAssigned(projectId, loggedId, page);
        if(employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<IdNameSurUserEmail> totalEmployees = userRepository.findEmployeesAssigned(projectId, loggedId, null);
        ListIdNameSurUserEmailTotal bodyTotal = new ListIdNameSurUserEmailTotal(employees, totalEmployees.size());
        return new ResponseEntity<>(bodyTotal, HttpStatus.OK);
    }
}
