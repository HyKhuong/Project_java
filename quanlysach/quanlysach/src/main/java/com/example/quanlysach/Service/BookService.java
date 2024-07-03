package com.example.quanlysach.Service;

import com.example.quanlysach.Repository.IBookRepository;
import com.example.quanlysach.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookService {

    @Autowired
    private IBookRepository bookRepository;
    public List<Book> getALLBooks() { return bookRepository.findAll();
    }
    public Book getBookById(Long id) {
        Optional<Book> optional = bookRepository.findById(id);
        return optional.orElse(null);
    }

    public Book addBook (Book book) { return bookRepository.save(book);
    }
    public Book updateBook (Book book) { return bookRepository.save(book);
    }
    public void deleteBook (Long id) { bookRepository.deleteById(id);
    }

}
