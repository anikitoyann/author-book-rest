package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/author")
@RestController
public class AuthorEndpoint {
    private final AuthorRepository authorRepository;

    @PostMapping()
    public ResponseEntity<Author> create(@RequestBody Author author) {
        Optional<Author> byEmail = authorRepository.findByEmail(author.getEmail());
        if (byEmail.isEmpty()) {
            author.setId(0);
            return ResponseEntity.ok(authorRepository.save(author));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping()
    public ResponseEntity<List<Author>> getAll() {
        List<Author> all = authorRepository.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") int id) {
        Optional<Author> byId = authorRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
