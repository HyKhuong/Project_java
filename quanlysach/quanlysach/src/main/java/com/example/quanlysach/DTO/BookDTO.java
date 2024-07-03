package com.example.quanlysach.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String categoryName;
}
