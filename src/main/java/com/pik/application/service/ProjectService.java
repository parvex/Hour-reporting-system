package com.pik.application.service;

import com.pik.application.domain.Project;
import com.pik.application.domain.User;
import com.pik.application.repository.ProjectRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public List<Project> projects(){
        return projectRepository.findAll();
    }

    public ResponseEntity<Project> projectByID(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<Project> createProject(Project project) {
        if(projectRepository.findOneByName(project.getName()) != null)
            throw new RuntimeException("Project name exists");
        return new ResponseEntity<>(projectRepository.save(project), HttpStatus.ACCEPTED);
    }

    public Project updateProject(Project project) {
        if (projectRepository.findOneByName(project.getName()) != null
                && !projectRepository.findOneByName(project.getName()).getId().equals(project.getId())) {
            throw new RuntimeException("Project name already exist");
        }
        return projectRepository.save(project);
    }

    public ResponseEntity<Project> deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            projectRepository.delete(project.get());
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }

    public List<Project> getProjectByPhrase(String phrase, List<Long> chosenId) {
        Pageable page = PageRequest.of(0, 10);
        User loggedUser = userService.getLoggedUser();
        Long loggedUserId = loggedUser.getId();
        return projectRepository.findByPhrase(phrase, chosenId, loggedUserId, page);
    }

    public Project findById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()){
            throw new RuntimeException("Project not found!");
        }
        return project.get();
    }
}
