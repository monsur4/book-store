package com.mon.bookstore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthorDto {
    @NotEmpty(message = "author name must not be empty")
    private String name;
}
