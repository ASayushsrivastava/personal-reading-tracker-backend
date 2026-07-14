package com.bookshelve.backend.service.impl;

import com.bookshelve.backend.dto.BookResponse;
import com.bookshelve.backend.dto.DashboardResponse;
import com.bookshelve.backend.entity.Book;
import com.bookshelve.backend.entity.ReadingStatus;
import com.bookshelve.backend.repository.BookRepository;
import com.bookshelve.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;

    @Override
    public DashboardResponse getDashboard() {
        long total = bookRepository.count();
        long reading = bookRepository.countByStatus(ReadingStatus.READING);
        long toRead = bookRepository.countByStatus(ReadingStatus.TO_READ);
        long completed = bookRepository.countByStatus(ReadingStatus.COMPLETED);
        long abandoned = bookRepository.countByStatus(ReadingStatus.ABANDONED);

        return DashboardResponse.builder()
                .totalBooks(total)
                .currentlyReading(reading)
                .toRead(toRead)
                .completed(completed)
                .abandoned(abandoned)
                .continueReading(getContinueReading())
                .recentlyAdded(getRecentlyAdded())
                .recentlyCompleted(getRecentlyCompleted())
                .build();
    }

    @Override
    public List<BookResponse> getContinueReading() {
        return bookRepository.findByStatusOrderByDateStartedDesc(ReadingStatus.READING).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getRecentlyAdded() {
        return bookRepository.findTop10ByOrderByDateAddedDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private List<BookResponse> getRecentlyCompleted() {
        return bookRepository.findTop10ByStatusOrderByDateCompletedDesc(ReadingStatus.COMPLETED).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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
