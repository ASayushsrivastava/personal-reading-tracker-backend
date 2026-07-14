package com.bookshelve.backend.controller;

import com.bookshelve.backend.dto.CollectionRequest;
import com.bookshelve.backend.dto.CollectionResponse;
import com.bookshelve.backend.service.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;
    
    @GetMapping
    public ResponseEntity<List<CollectionResponse>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponse> getCollectionById(@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    @PostMapping
    public ResponseEntity<CollectionResponse> createCollection(@Valid @RequestBody CollectionRequest request) {
        CollectionResponse created = collectionService.createCollection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionResponse> renameCollection(
            @PathVariable Long id, @Valid @RequestBody CollectionRequest request) {
        return ResponseEntity.ok(collectionService.renameCollection(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/books")
    public ResponseEntity<CollectionResponse> addBookToCollection(
            @PathVariable Long id, @RequestBody AddBookRequest request) {
        return ResponseEntity.ok(collectionService.addBookToCollection(id, request.getBookId()));
    }

    @DeleteMapping("/{id}/books/{bookId}")
    public ResponseEntity<Void> removeBookFromCollection(@PathVariable Long id, @PathVariable Long bookId) {
        collectionService.removeBookFromCollection(id, bookId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Small inline request body for adding a book to a collection by id.
     */
    public static class AddBookRequest {
        private Long bookId;

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }
    }
}
