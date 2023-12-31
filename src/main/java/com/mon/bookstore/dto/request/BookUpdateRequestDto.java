package com.mon.bookstore.dto.request;

import com.mon.bookstore.dto.AuthorDto;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BookUpdateRequestDto {
    private AuthorDto author;
    private String isbn;
    private Boolean isAvailable;
    @PositiveOrZero
    private Integer pageCount;
    private String title;
}
