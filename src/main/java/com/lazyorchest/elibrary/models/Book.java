package com.lazyorchest.elibrary.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(length = 100, unique = true)
    private String title;
    @Column(length = 100)
    private String author;
    @Column(length = 50)
    private String publisher;
    @Column(name = "publication_year")
    private Integer publicationYear;
    private Integer edition;
    @Column(name = "page_size")
    private Integer pageSize;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private UUID createdBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private UUID updatedBy;
}
