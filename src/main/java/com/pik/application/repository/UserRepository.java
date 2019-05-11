package com.pik.application.repository;

import com.pik.application.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

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

    @Query("SELECT u.id, u.name, u.surname FROM User u WHERE upper(u.name) LIKE CONCAT('%',upper(:phrase),'%')" +
            "OR upper(u.surname) LIKE CONCAT('%',upper(:phrase),'%') AND u.id NOT IN :chosenId AND u.supervisor.id = :supervisorId")
    List<User> findByUsernameLike(String phrase, List<Long> chosenId, Long supervisorId, Pageable pageable);

}
