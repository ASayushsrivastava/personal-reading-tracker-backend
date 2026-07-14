package com.bookshelve.backend.controller;

import com.bookshelve.backend.dto.BookResponse;
import com.bookshelve.backend.dto.DashboardResponse;
import com.bookshelve.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }

    @GetMapping("/continue-reading")
    public ResponseEntity<List<BookResponse>> getContinueReading() {
        return ResponseEntity.ok(dashboardService.getContinueReading());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<BookResponse>> getRecentlyAdded() {
        return ResponseEntity.ok(dashboardService.getRecentlyAdded());
    }
}
