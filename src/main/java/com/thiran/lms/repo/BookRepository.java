package com.thiran.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiran.lms.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookNameContainingOrAuthorContaining(String bookName, String author);
}
