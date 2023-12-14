package com.mon.bookstore.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetrievedBooksResponseDto {
    private String title;
    private String author;
    private String isbn;
    private Integer numberRetrieved;
    private Integer numberRemaining;
}
