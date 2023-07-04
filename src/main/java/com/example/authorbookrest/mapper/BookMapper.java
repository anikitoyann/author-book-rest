package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.CreateBookRequestDto;
import com.example.authorbookrest.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {
    @Value("${site.url}")
    String siteUrl;

    @Mapping(target = "author.id", source = "authorId")
   public abstract Book map(CreateBookRequestDto dto);

    @Mapping(target = "authorDto", source = "author")
    @Mapping(target = "picUrl", expression = "java(entity.getPicName()!=null?siteUrl+\"/books/getImage?picName=\"+entity.getPicName(): null)")
   public abstract BookDto mapToDto(com.example.authorbookrest.entity.Book entity);

   public abstract List<BookDto> mapToListDtos(List<Book> books);

}
