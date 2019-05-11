package com.pik.application.repository;

import com.pik.application.domain.Project;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findOneByName(String name);

    @Query("SELECT p FROM Project p")
    List<Project> getLimited(Pageable page);

    default Optional<Project> getFirst() {
        return getLimited(PageRequest.of(0,1))
                .stream()
                .findFirst();
    }

    @Query("SELECT p.id, p.name FROM User u, Project p WHERE p MEMBER OF u.projects AND upper(p.name) LIKE CONCAT('%',upper(:phrase),'%')" +
            " AND p.id NOT IN :chosenId AND u.username = :loggedUser")
    List<Project> findByPhrase(String phrase, List<Long> chosenId, String loggedUser, Pageable pageable);


}
