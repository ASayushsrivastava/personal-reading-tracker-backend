package com.bookshelve.backend.dto;

import com.bookshelve.backend.entity.ReadingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private ReadingStatus status;
    private Integer currentPage;
    private Integer totalPages;
    private Double progress;
    private Integer rating;
    private String notes;
    private String coverImageUrl;
    private LocalDateTime dateAdded;
    private LocalDate dateStarted;
    private LocalDate dateCompleted;
}
