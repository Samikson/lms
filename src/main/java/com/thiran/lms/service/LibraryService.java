package com.thiran.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiran.lms.data.Response;
import com.thiran.lms.entity.Book;
import com.thiran.lms.repo.BookRepository;


@Service
public class LibraryService {
	
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByBookNameContainingOrAuthorContaining(keyword, keyword);
    }

    public Optional<?> checkoutBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent() && book.get().isAvailabilityStatus()) {
            book.get().setAvailabilityStatus(false);
            bookRepository.save(book.get());
            return book;
        }
        String message = book.isPresent() ? "Book is Not Available" : "Book Is Not Present for given Id" ;
        return Optional.of(Response.builder().status(false).message(message).build());
    }

    public Optional<?> returnBook(Long id, int overdueDays) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent() && !book.get().isAvailabilityStatus()) {
            book.get().setAvailabilityStatus(true);
            bookRepository.save(book.get());
            double fine = calculateFine(overdueDays);
            System.out.println("Book returned. Overdue fine: $" + fine);
            return book;
        }
        String message = book.isPresent() ? "Book is Not Available" : "Book Is Not Present for given Id" ;
        return Optional.of(Response.builder().status(false).message(message).build());
    }

    private double calculateFine(int overdueDays) {
        final double FINE_RATE_PER_DAY = 0.50;
        return overdueDays * FINE_RATE_PER_DAY;
    }
}
