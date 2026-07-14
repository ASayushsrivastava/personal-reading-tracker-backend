package com.bookshelve.backend.service.impl;

import com.bookshelve.backend.dto.BookRequest;
import com.bookshelve.backend.dto.BookResponse;
import com.bookshelve.backend.entity.Book;
import com.bookshelve.backend.entity.ReadingStatus;
import com.bookshelve.backend.exception.ResourceNotFoundException;
import com.bookshelve.backend.repository.BookRepository;
import com.bookshelve.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = findBookOrThrow(id);
        return toResponse(book);
    }

    @Override
    public BookResponse createBook(BookRequest request) {
        Book book = new Book();
        applyRequestToEntity(request, book);
        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findBookOrThrow(id);
        applyRequestToEntity(request, book);
        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = findBookOrThrow(id);
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String title, String author, String genre, String status) {
        List<Book> books;

        if (status != null && !status.isBlank()) {
            ReadingStatus readingStatus = parseStatus(status);
            books = bookRepository.findByStatus(readingStatus);
        } else if (title != null && !title.isBlank()) {
            books = bookRepository.search(title);
        } else if (author != null && !author.isBlank()) {
            books = bookRepository.search(author);
        } else if (genre != null && !genre.isBlank()) {
            books = bookRepository.findByGenreIgnoreCase(genre);
        } else {
            books = bookRepository.findAll();
        }

        return books.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ---- helpers ----

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Book", id));
    }

    private ReadingStatus parseStatus(String status) {
        try {
            return ReadingStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid reading status: " + status);
        }
    }

    private void applyRequestToEntity(BookRequest request, Book book) {
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());

        ReadingStatus newStatus = request.getStatus() != null ? request.getStatus() : book.getStatus();
        if (newStatus == null) {
            newStatus = ReadingStatus.TO_READ;
        }

        // Auto-manage dateStarted / dateCompleted based on status transitions,
        // unless the caller explicitly supplied a value.
        if (newStatus == ReadingStatus.READING && book.getDateStarted() == null && request.getDateStarted() == null) {
            book.setDateStarted(LocalDate.now());
        }
        if (newStatus == ReadingStatus.COMPLETED && book.getDateCompleted() == null && request.getDateCompleted() == null) {
            book.setDateCompleted(LocalDate.now());
        }

        if (request.getDateStarted() != null) {
            book.setDateStarted(request.getDateStarted());
        }
        if (request.getDateCompleted() != null) {
            book.setDateCompleted(request.getDateCompleted());
        }

        book.setStatus(newStatus);
        book.setCurrentPage(request.getCurrentPage() != null ? request.getCurrentPage() : 0);
        book.setTotalPages(request.getTotalPages());
        book.setRating(request.getRating());
        book.setNotes(request.getNotes());

        if (request.getCoverImageUrl() != null) {
            book.setCoverImageUrl(request.getCoverImageUrl());
        }
    }

    private BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .status(book.getStatus())
                .currentPage(book.getCurrentPage())
                .totalPages(book.getTotalPages())
                .progress(book.getProgress())
                .rating(book.getRating())
                .notes(book.getNotes())
                .coverImageUrl(book.getCoverImageUrl())
                .dateAdded(book.getDateAdded())
                .dateStarted(book.getDateStarted())
                .dateCompleted(book.getDateCompleted())
                .build();
    }
}
