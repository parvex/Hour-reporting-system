package com.pik.application.repository;

import com.pik.application.domain.User;
import com.pik.application.dto.IdName;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUsername(String username);

    @Query("SELECT u FROM User u")
    List<User> getLimited(Pageable page);

    default Optional<User> getFirst() {
        return getLimited(PageRequest.of(0,1))
                .stream()
                .findFirst();
    }

    @Query("select u from User u where :role MEMBER OF u.roles")
    List<User> findByRoles(@Param("role") String role);

    @Query("SELECT new com.pik.application.dto.IdName(u.id, CONCAT(u.name,' ',u.surname)) FROM User u WHERE (CONCAT(upper(u.name),' ', u.surname) LIKE CONCAT('%',upper(:phrase),'%')" +
            " OR :phrase IS NULL) AND ((COALESCE(:chosenIds, NULL) IS NULL) OR u.id NOT IN (:chosenIds)) AND u.supervisor.id = :supervisorId")
    List<IdName> findByUsernameLike(@Nullable String phrase, @Nullable List<Long> chosenIds, Long supervisorId, Pageable pageable);

    @Query("SELECT new com.pik.application.dto.IdName(u.id, CONCAT(u.name,' ', u.surname, ' [', u.username, ']')) FROM User u WHERE CONCAT(upper(u.name), ' ' , upper(u.surname)) " +
            " LIKE CONCAT('%',upper(:phrase),'%') AND 'SUPERVISOR' MEMBER OF u.roles")
    List<IdName> findSupervisorsByUsernameLike(String phrase, Pageable pageable);
}
