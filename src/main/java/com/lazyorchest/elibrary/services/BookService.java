package com.lazyorchest.elibrary.services;

import com.lazyorchest.elibrary.dto.BookRequest;
import com.lazyorchest.elibrary.dto.DataResponse;
import com.lazyorchest.elibrary.models.Book;
import com.lazyorchest.elibrary.models.User;
import com.lazyorchest.elibrary.repositories.BookRepository;
import com.lazyorchest.elibrary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    public Book createBook(BookRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .publicationYear(request.getPublicationYear())
                .edition(request.getEdition())
                .pageSize(request.getPageSize())
                .createdAt(LocalDateTime.now())
                .createdBy(user.getId())
                .updatedAt(LocalDateTime.now())
                .updatedBy(user.getId())
                .build();
        bookRepository.save(book);
        return book;
    }
}
