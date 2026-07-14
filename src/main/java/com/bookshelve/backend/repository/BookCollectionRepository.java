package com.bookshelve.backend.repository;

import com.bookshelve.backend.entity.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {

    List<BookCollection> findByCollectionId(Long collectionId);

    List<BookCollection> findByBookId(Long bookId);

    Optional<BookCollection> findByBookIdAndCollectionId(Long bookId, Long collectionId);

    boolean existsByBookIdAndCollectionId(Long bookId, Long collectionId);

    long countByCollectionId(Long collectionId);

    void deleteByBookIdAndCollectionId(Long bookId, Long collectionId);
}
