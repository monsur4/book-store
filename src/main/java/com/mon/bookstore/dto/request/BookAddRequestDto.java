package com.mon.bookstore.dto.request;

import com.mon.bookstore.dto.AuthorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookAddRequestDto {
    @Valid
    private AuthorDto author;
    @NotEmpty(message = "title must not be empty")
    private String isbn;
    @NotNull(message = "isAvailable must not be null")
    private Boolean isAvailable;
    @Positive(message = "pageCount must be a positive number")
    private Integer pageCount;
    @NotEmpty(message = "title must not be empty")
    private String title;
}
