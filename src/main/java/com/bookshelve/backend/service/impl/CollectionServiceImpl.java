package com.bookshelve.backend.service.impl;

import com.bookshelve.backend.dto.BookResponse;
import com.bookshelve.backend.dto.CollectionRequest;
import com.bookshelve.backend.dto.CollectionResponse;
import com.bookshelve.backend.entity.Book;
import com.bookshelve.backend.entity.BookCollection;
import com.bookshelve.backend.entity.Collection;
import com.bookshelve.backend.exception.ResourceNotFoundException;
import com.bookshelve.backend.repository.BookCollectionRepository;
import com.bookshelve.backend.repository.BookRepository;
import com.bookshelve.backend.repository.CollectionRepository;
import com.bookshelve.backend.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final BookRepository bookRepository;
    private final BookCollectionRepository bookCollectionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CollectionResponse> getAllCollections() {
        return collectionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CollectionResponse getCollectionById(Long id) {
        Collection collection = findCollectionOrThrow(id);
        return toResponse(collection);
    }

    @Override
    public CollectionResponse createCollection(CollectionRequest request) {
        if (collectionRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("A collection named '" + request.getName() + "' already exists");
        }
        Collection collection = Collection.builder()
                .name(request.getName())
                .build();
        Collection saved = collectionRepository.save(collection);
        return toResponse(saved);
    }

    @Override
    public CollectionResponse renameCollection(Long id, CollectionRequest request) {
        Collection collection = findCollectionOrThrow(id);
        if (!collection.getName().equalsIgnoreCase(request.getName())
                && collectionRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("A collection named '" + request.getName() + "' already exists");
        }
        collection.setName(request.getName());
        Collection saved = collectionRepository.save(collection);
        return toResponse(saved);
    }

    @Override
    public void deleteCollection(Long id) {
        Collection collection = findCollectionOrThrow(id);
        collectionRepository.delete(collection);
    }

    @Override
    public CollectionResponse addBookToCollection(Long collectionId, Long bookId) {
        Collection collection = findCollectionOrThrow(collectionId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Book", bookId));

        if (bookCollectionRepository.existsByBookIdAndCollectionId(bookId, collectionId)) {
            throw new IllegalArgumentException("Book is already in this collection");
        }

        BookCollection bookCollection = BookCollection.builder()
                .book(book)
                .collection(collection)
                .build();
        bookCollectionRepository.save(bookCollection);

        return toResponse(findCollectionOrThrow(collectionId));
    }

    @Override
    public void removeBookFromCollection(Long collectionId, Long bookId) {
        // Ensure the collection exists before attempting the deletion.
        findCollectionOrThrow(collectionId);
        if (!bookCollectionRepository.existsByBookIdAndCollectionId(bookId, collectionId)) {
            throw new ResourceNotFoundException("Book " + bookId + " is not part of collection " + collectionId);
        }
        bookCollectionRepository.deleteByBookIdAndCollectionId(bookId, collectionId);
    }

    // ---- helpers ----

    private Collection findCollectionOrThrow(Long id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Collection", id));
    }

    private CollectionResponse toResponse(Collection collection) {
        List<BookResponse> books = collection.getBookCollections().stream()
                .map(BookCollection::getBook)
                .map(this::toBookResponse)
                .collect(Collectors.toList());

        return CollectionResponse.builder()
                .id(collection.getId())
                .name(collection.getName())
                .dateCreated(collection.getDateCreated())
                .bookCount(books.size())
                .books(books)
                .build();
    }

    private BookResponse toBookResponse(Book book) {
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
