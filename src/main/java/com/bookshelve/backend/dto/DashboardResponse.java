package com.bookshelve.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalBooks;
    private long currentlyReading;
    private long toRead;
    private long completed;
    private long abandoned;

    private List<BookResponse> continueReading;
    private List<BookResponse> recentlyAdded;
    private List<BookResponse> recentlyCompleted;
}
