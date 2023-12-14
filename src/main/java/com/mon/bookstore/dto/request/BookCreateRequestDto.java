package com.mon.bookstore.dto.request;

import com.mon.bookstore.dto.AuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BookCreateRequestDto {
    @NotEmpty(message = "title must not be empty")
    private String title;
    @NotEmpty(message = "title must not be empty")
    @PositiveOrZero
    private Integer pageCount;
    @Valid
    private AuthorDto author;
    @NotEmpty(message = "title must not be empty")
    private String isbn;
}
