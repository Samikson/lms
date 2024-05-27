package com.thiran.lms.ctrl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thiran.lms.entity.Book;
import com.thiran.lms.service.LibraryService;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return libraryService.addBook(book);
    }
    
    @DeleteMapping("/books/{id}")
    public void addBook(@PathVariable Long id) {
        libraryService.deleteBook(id);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/books/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return libraryService.searchBooks(keyword);
    }

    @PostMapping("/books/{id}/checkout")
    public Optional<?> checkoutBook(@PathVariable Long id) {
        return libraryService.checkoutBook(id);
    }

    @PostMapping("/books/{id}/return")
    public Optional<?> returnBook(@PathVariable Long id, @RequestParam int overdueDays) {
        return libraryService.returnBook(id, overdueDays);
    }
}

