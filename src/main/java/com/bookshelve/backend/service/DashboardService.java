package com.bookshelve.backend.service;

import com.bookshelve.backend.dto.BookResponse;
import com.bookshelve.backend.dto.DashboardResponse;

import java.util.List;

public interface DashboardService {

    DashboardResponse getDashboard();

    List<BookResponse> getContinueReading();

    List<BookResponse> getRecentlyAdded();
}
