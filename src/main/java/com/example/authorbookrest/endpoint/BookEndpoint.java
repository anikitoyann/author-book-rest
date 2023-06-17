package com.example.authorbookrest.endpoint;
import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.CreateBookRequestDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookEndpoint {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody CreateBookRequestDto createBookRequestDto) {
        Optional<Author> byId = authorRepository.findById(createBookRequestDto.getAuthorId());
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Book saved=bookRepository.save(bookMapper.map(createBookRequestDto));
        saved.setAuthor(byId.get());
        return ResponseEntity.ok(bookMapper.mapToDto(saved));
    }
}
