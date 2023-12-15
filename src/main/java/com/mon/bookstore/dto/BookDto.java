package com.mon.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private String title;
    private Integer pageCount;
    private String author;
    private String isbn;
    private Boolean isAvailable;
}
