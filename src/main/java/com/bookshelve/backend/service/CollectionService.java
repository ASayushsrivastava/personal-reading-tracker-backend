package com.bookshelve.backend.service;

import com.bookshelve.backend.dto.CollectionRequest;
import com.bookshelve.backend.dto.CollectionResponse;

import java.util.List;

public interface CollectionService {

    List<CollectionResponse> getAllCollections();

    CollectionResponse getCollectionById(Long id);

    CollectionResponse createCollection(CollectionRequest request);

    CollectionResponse renameCollection(Long id, CollectionRequest request);

    void deleteCollection(Long id);

    CollectionResponse addBookToCollection(Long collectionId, Long bookId);

    void removeBookFromCollection(Long collectionId, Long bookId);
}
