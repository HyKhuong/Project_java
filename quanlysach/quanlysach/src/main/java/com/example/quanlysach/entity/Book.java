package com.example.quanlysach.entity;
import com.example.quanlysach.Validator.annotation.ValidCategoryId;
import com.example.quanlysach.Validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @NotEmpty(message = "Title must not be null")
    @Size(max =50,min=1,message = "Title must be less than 50 characters")
    private String title;
    @Column(name = "author")
    private String author;

    @Column(name = "price")
    @NotNull(message = "price is required")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;
}