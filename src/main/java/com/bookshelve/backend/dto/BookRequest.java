package com.bookshelve.backend.dto;

import com.bookshelve.backend.entity.ReadingStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    private String genre;

    private ReadingStatus status;

    @PositiveOrZero(message = "Current page cannot be negative")
    private Integer currentPage;

    @PositiveOrZero(message = "Total pages cannot be negative")
    private Integer totalPages;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    private String notes;

    private String coverImageUrl;

    private LocalDate dateStarted;

    private LocalDate dateCompleted;
}
