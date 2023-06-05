package com.example.authorbookrest.repository;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

}