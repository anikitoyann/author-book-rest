package com.example.authorbookrest.endpoint;
import com.example.authorbookrest.dto.AuthorDto;
import com.example.authorbookrest.dto.CreateAuthorRequestDto;
import com.example.authorbookrest.dto.CreateAuthorResponseDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.mapper.AuthorMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/author")
@RestController
public class AuthorEndpoint {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


    @PostMapping()
    public ResponseEntity<CreateAuthorResponseDto> create(@RequestBody CreateAuthorRequestDto requestDto, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        Optional<Author> byEmail = authorRepository.findByEmail(requestDto.getEmail());
        if (byEmail.isEmpty()) {
            Author author = authorMapper.map(requestDto);
            author.setUser(user);
            authorRepository.save(author);
            return ResponseEntity.ok(authorMapper.map(author));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping()
    public ResponseEntity<List<AuthorDto>> getAll(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Author> all = authorRepository.findAllByUser(user);
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<AuthorDto>authorDtos=new ArrayList<>();
        for (Author author : all) {
            authorDtos.add(authorMapper.mapToDto(author));
        }
        return ResponseEntity.ok(authorDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") int id) {
        Optional<Author> byId = authorRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable("id") int id, @RequestBody Author author) {
        Optional<Author> byId = authorRepository.findById(id);
        if (byId.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        Optional<Author> byEmail = authorRepository.findByEmail(author.getEmail());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Author authorFromDB = byId.get();
        if (author.getName() != null && !author.getName().isEmpty()) {
            authorFromDB.setName(author.getName());
        }
        if (author.getSurname() != null && !author.getSurname().isEmpty()) {
            authorFromDB.setSurname(author.getSurname());
        }
        if (author.getEmail() != null && !author.getEmail().isEmpty()) {
            authorFromDB.setEmail(author.getEmail());
        }

        return ResponseEntity.ok(authorRepository.save(authorFromDB));
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
