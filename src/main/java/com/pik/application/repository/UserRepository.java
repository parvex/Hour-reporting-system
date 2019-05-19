package com.pik.application.repository;

import com.pik.application.domain.User;
import com.pik.application.dto.EmployeeData.IdNameSurEmailSupervisor_NameProjects;
import com.pik.application.dto.EmployeeData.IdUserNameSurEmailProjectsSupervisorRoles;
import com.pik.application.dto.LongString;
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

    @Query("SELECT new com.pik.application.dto.LongString(u.id, CONCAT(u.name,' ',u.surname)) FROM User u WHERE (CONCAT(upper(u.name),' ', u.surname) LIKE CONCAT('%',upper(:phrase),'%') " +
            "OR :phrase IS NULL) AND ((COALESCE(:chosenIds, NULL) IS NULL) OR (u.id NOT IN (:chosenIds))) AND (u.supervisor.id = :loggedId OR :loggedId = 1811)")
    List<LongString> findByUsernameLike(@Nullable String phrase, @Nullable List<Long> chosenIds, Long loggedId, Pageable pageable);

    @Query("SELECT new com.pik.application.dto.LongString(u.id, CONCAT(u.name,' ', u.surname, ' [', u.username, ']')) FROM User u WHERE CONCAT(upper(u.name), ' ' , upper(u.surname)) " +
            "LIKE CONCAT('%',upper(:phrase),'%') AND 'SUPERVISOR' MEMBER OF u.roles")
    List<LongString> findSupervisorsByUsernameLike(String phrase, Pageable pageable);

    @Query("SELECT DISTINCT new com.pik.application.dto.EmployeeData.IdNameSurEmailSupervisor_NameProjects(u.id, u.name, u.surname, u.email, CONCAT(u.supervisor.name,' ',u.supervisor.surname)) " +
            "FROM User u LEFT JOIN u.projects p WHERE (:email IS NULL OR :email = '' OR upper(u.email) LIKE CONCAT('%',upper(:email),'%')) AND (:name IS NULL OR :name = '' " +
            "OR upper(u.name) LIKE CONCAT('%',upper(:name),'%')) AND (:surname IS NULL OR :surname = '' OR upper(u.surname) LIKE CONCAT('%',upper(:surname),'%')) " +
            "AND (p.id IN (:projects) OR (-1 IN (:projects)) OR COALESCE(:projects, NULL) IS NULL) AND (:loggedId = u.supervisor.id OR :loggedId = 1811)")
    List<IdNameSurEmailSupervisor_NameProjects> findByIdNameSurEmailSupervisorPage(@Nullable String email, @Nullable String name,
                                                                                   @Nullable String surname, @Nullable List<Long> projects, Long loggedId, Pageable page);

    @Query("SELECT new com.pik.application.dto.EmployeeData.IdUserNameSurEmailProjectsSupervisorRoles(u.id, u.username, u.name, u.surname, u.email, ''," +
            "u.supervisor.id, CONCAT(u.supervisor.name,' ',u.supervisor.surname)) FROM User u WHERE (u.id = :id OR (-1 = :id)) AND (u.supervisor.id = :loggedId OR :loggedId = 1811)")
    Optional<IdUserNameSurEmailProjectsSupervisorRoles> getEmployeeById(Long id, Long loggedId);

    @Query("SELECT r FROM User u JOIN u.roles r WHERE u.id = :id")
    List<String> findAllRolesForUser(Long id);

    @Query("SELECT new com.pik.application.dto.EmployeeData.IdUserNameSurEmailProjectsSupervisorRoles(u.id, u.username, u.name, u.surname, u.email, ''," +
            "u.supervisor.id, CONCAT(u.supervisor.name,' ',u.supervisor.surname)) FROM User u")
    List<IdUserNameSurEmailProjectsSupervisorRoles> findAllEmployees();

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
