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

    @Query("SELECT p FROM Project p")
    List<Project> getLimited(Pageable page);

    default Optional<Project> getFirst() {
        return getLimited(PageRequest.of(0,1))
                .stream()
                .findFirst();
    }
}
