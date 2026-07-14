package com.bookshelve.backend.service;

import com.bookshelve.backend.dto.BookRequest;
import com.bookshelve.backend.dto.BookResponse;

import java.util.List;

public interface BookService {

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Long id);

    BookResponse createBook(BookRequest request);

    BookResponse updateBook(Long id, BookRequest request);

    void deleteBook(Long id);

    List<BookResponse> searchBooks(String title, String author, String genre, String status);
}
