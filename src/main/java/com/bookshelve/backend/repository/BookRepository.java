package com.bookshelve.backend.repository;

import com.bookshelve.backend.entity.Book;
import com.bookshelve.backend.entity.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByStatus(ReadingStatus status);

    long countByStatus(ReadingStatus status);

    List<Book> findByStatusOrderByDateStartedDesc(ReadingStatus status);

    List<Book> findTop10ByOrderByDateAddedDesc();

    List<Book> findTop10ByStatusOrderByDateCompletedDesc(ReadingStatus status);

    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> search(@Param("keyword") String keyword);

    List<Book> findByGenreIgnoreCase(String genre);

    List<Book> findByDateCompletedAfter(LocalDate date);
}
