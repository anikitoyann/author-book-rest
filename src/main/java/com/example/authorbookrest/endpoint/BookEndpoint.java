package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.CreateBookRequestDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.Currency;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.repository.BookRepository;
import com.example.authorbookrest.repository.CurrencyRepository;
import com.example.authorbookrest.util.RoundUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookEndpoint {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CurrencyRepository currencyRepository;

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody CreateBookRequestDto createBookRequestDto) {
        Optional<Author> byId = authorRepository.findById(createBookRequestDto.getAuthorId());
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Book saved = bookRepository.save(bookMapper.map(createBookRequestDto));
        saved.setAuthor(byId.get());
        return ResponseEntity.ok(bookMapper.mapToDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        List<Book> all = bookRepository.findAll();
        List<BookDto> bookDtos = bookMapper.mapToListDtos(all);
        List<Currency> currencies = currencyRepository.findAll();
        if(!currencies.isEmpty()){
            Currency currency = currencies.get(0);

            for (BookDto bookDto : bookDtos) {
                double priceAmd = bookDto.getPriceAmd();
                bookDto.setPriceRub(RoundUtil.round(priceAmd/currency.getRub(), 2));
                bookDto.setPriceUsd(RoundUtil.round(priceAmd / currency.getUsd(),2));

            }

        }
        return ResponseEntity.ok(bookDtos);
    }

}
