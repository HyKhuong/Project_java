package com.example.quanlysach.Controller;

import com.example.quanlysach.DTO.BookDTO;
import com.example.quanlysach.Service.BookService;
import com.example.quanlysach.Service.CategoryService;
import com.example.quanlysach.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
public class APIController {

    private final BookService bookService;

    private final CategoryService categoryService;
    private BookDTO convertToBookDto(Book book){
        BookDTO bookDTO=new BookDTO();
        bookDTO.setId((book.getId()));
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setCategoryName(categoryService.getCategoryById(book.getCategory().getId()).getName());
        return bookDTO;
    }

    @GetMapping
    @ResponseBody
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookService.getALLBooks();
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book :books){
            bookDTOS.add(convertToBookDto(book));
        }
        return bookDTOS;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookDTO getBookId(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return convertToBookDto(book);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteBook(@PathVariable Long id){
        if (bookService.getBookById(id)!= null)
            bookService.deleteBook(id);
    }
    @PostMapping
    @ResponseBody
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "redirect:/api/v1/books";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateProduct(@PathVariable Long id,
                                                 @RequestBody Book bookDetails) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new RuntimeException("Product not found on :: " +id);
        }
        book.setTitle(bookDetails.getTitle());
        book.setPrice(bookDetails.getPrice());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        final Book updatedProduct =bookService.addBook(book);
        return ResponseEntity.ok(updatedProduct);
    }
}
