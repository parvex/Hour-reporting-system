package com.pik.application.repository;

import com.pik.application.domain.Project;
import com.pik.application.dto.LongString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
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

    @Query("SELECT DISTINCT new com.pik.application.dto.LongString(p.id, p.name) FROM User u, Project p WHERE (p MEMBER OF u.projects) AND (upper(p.name) LIKE CONCAT('%',upper(:phrase),'%')" +
            " OR :phrase IS NULL) AND (p.id NOT IN (:chosenIds) OR COALESCE(:chosenIds, NULL) IS NULL) AND (u.id = :loggedId OR u.supervisor.id = :loggedId OR :loggedId=1811)")
    List<LongString> findByPhrase(@Nullable String phrase, @Nullable List<Long> chosenIds, Long loggedId, Pageable pageable);

    @Query("SELECT new com.pik.application.dto.LongString(p.id, p.name) FROM User u, Project p WHERE (p MEMBER OF u.projects) AND u.id = :id ")
    List<LongString> findProjectsForUser(Long id);
}
