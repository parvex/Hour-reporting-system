package com.pik.application.service;

import com.pik.application.domain.Project;
import com.pik.application.domain.User;
import com.pik.application.dto.EmployeeData.EmailNameSurProjects;
import com.pik.application.dto.EmployeeData.IdNameSurEmailSupervisor_Name;
import com.pik.application.dto.EmployeeData.IdUserNameSurEmailProjectsSupervisorRoles;
import com.pik.application.dto.EmployeeData.ListIdNameSurEmailSupervisor_NameTotal;
import com.pik.application.dto.LongString;
import com.pik.application.dto.PageOptions;
import com.pik.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
//        Pageable page = PageRequest.of(0, 10);
        Long loggedId = getLoggedUser().getId();
        if(chosenIds != null && chosenIds.isEmpty())
            chosenIds.add(-1L);
        List<LongString> body = userRepository.findByUsernameLike(phrase, chosenIds, loggedId, page);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<ListIdNameSurEmailSupervisor_NameTotal> getEmployeesPage(EmailNameSurProjects employee, PageOptions pageOptions) {
        Pageable page = PageRequest.of(pageOptions.getPage(), pageOptions.getCount());
        Pageable pageMax = PageRequest.of(pageOptions.getPage(), Integer.MAX_VALUE);

        if(employee.getProjects() != null && employee.getProjects().isEmpty())
            employee.getProjects().add(-1L);

        Long loggedId = getLoggedUser().getId();
        List<IdNameSurEmailSupervisor_Name> body = userRepository.findByIdNameSurEmailSupervisorPage(employee.getEmail(),
                employee.getName(), employee.getSurname(), employee.getProjects(), loggedId, page);

        List<IdNameSurEmailSupervisor_Name> bodyMax = userRepository.findByIdNameSurEmailSupervisorPage(employee.getEmail(),
                employee.getName(), employee.getSurname(), employee.getProjects(), loggedId, pageMax);

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
            long num = 1L;
            for (String r : roles) {
                rolesNum.add(new LongString(num++, r));
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

    public Boolean checlIfHasRole(Long id, String role){
        List<String> roles = findAllRolesForUser(id);
        return roles.contains(role) ? true : false;
    }

    public ResponseEntity<IdUserNameSurEmailProjectsSupervisorRoles> updateEmployee(IdUserNameSurEmailProjectsSupervisorRoles body) {

        Optional<User> findUser = userRepository.findById(body.getId());
        if(findUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        User user = findUser.get();
        if (!body.getUsername().equals(user.getUsername()))
            user.setUsername(body.getUsername());
        if (!body.getEmail().equals(user.getEmail()))
            user.setEmail(body.getEmail());
        if (!body.getName().equals(user.getName()))
            user.setName(body.getName());
        if (!body.getSurname().equals(user.getSurname()))
            user.setSurname(body.getSurname());
        if (!body.getSupervisorId().equals(user.getSupervisor().getId())) {
            Optional<User> supervisor = userRepository.findById(body.getSupervisorId());
            if(checlIfHasRole(supervisor.get().getId(), "SUPERVISOR")) // change only if selected user is a supervisor
                user.setSupervisor(supervisor.get());
        }
        Set<Project> newProjects = new HashSet<>();
        for (LongString project : body.getProjects()) {
            newProjects.add(projectService.findById(project.getId())); // return 500 if projects doesnt exist
        }
        user.setProjects(newProjects);

        userRepository.save(user); // update user
        return getEmployeeById(user.getId());
    }
}
