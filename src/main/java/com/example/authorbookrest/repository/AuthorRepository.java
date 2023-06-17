package com.example.authorbookrest.repository;

import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByEmail(String email);
    List<Author> findAllByUser(User user);
}