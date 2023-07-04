package com.example.authorbookrest.dto;

import com.example.authorbookrest.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private int id;
    private String title;
    private String description;
    private String picUrl;
    private Language language;
    private double priceAmd;
    private double priceUsd;
    private double priceRub;
    private AuthorDto authorDto;
}