package com.bookshelve.backend.repository;

import com.bookshelve.backend.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Optional<Collection> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
