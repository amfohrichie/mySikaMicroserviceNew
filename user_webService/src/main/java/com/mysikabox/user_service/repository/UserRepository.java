package com.mysikabox.user_service.repository;

import com.mysikabox.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    User findUserByFirstNameContainingIgnoreCase(String firstName);

    User findUserByFirstNameAndLastName(String firstName, String lastName);
    User findUserByLastName(String lastName);
}
