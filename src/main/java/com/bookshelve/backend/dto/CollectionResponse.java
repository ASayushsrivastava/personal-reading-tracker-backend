package com.bookshelve.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionResponse {

    private Long id;
    private String name;
    private LocalDateTime dateCreated;
    private int bookCount;
    private List<BookResponse> books;
}
