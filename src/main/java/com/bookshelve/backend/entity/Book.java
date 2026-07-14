package com.bookshelve.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReadingStatus status = ReadingStatus.TO_READ;

    @Column(name = "current_page")
    @Builder.Default
    private Integer currentPage = 0;

    @Column(name = "total_pages")
    private Integer totalPages;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @Column(name = "date_started")
    private LocalDate dateStarted;

    @Column(name = "date_completed")
    private LocalDate dateCompleted;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BookCollection> bookCollections = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (dateAdded == null) {
            dateAdded = LocalDateTime.now();
        }
        if (status == null) {
            status = ReadingStatus.TO_READ;
        }
        if (currentPage == null) {
            currentPage = 0;
        }
    }

    /**
     * Reading progress percentage, calculated as (currentPage / totalPages) * 100.
     * Returns 0 if totalPages is null or zero to avoid division errors.
     */
    @Transient
    public double getProgress() {
        if (totalPages == null || totalPages == 0 || currentPage == null) {
            return 0.0;
        }
        double progress = (currentPage.doubleValue() / totalPages.doubleValue()) * 100.0;
        return Math.round(progress * 100.0) / 100.0;
    }
}
