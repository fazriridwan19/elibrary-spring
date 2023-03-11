package com.lazyorchest.elibrary.controllers;

import com.lazyorchest.elibrary.dto.BookRequest;
import com.lazyorchest.elibrary.dto.DataResponse;
import com.lazyorchest.elibrary.models.Book;
import com.lazyorchest.elibrary.services.BookService;
import com.lazyorchest.elibrary.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final JwtService jwtService;
    @Operation(summary = "Add new book", description = "This is endpoint proposed to creating new book", tags = "Book Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book has been created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DataResponse<Book>> createBook(@RequestBody BookRequest bookRequest, HttpServletRequest request, HttpServletResponse response) {
        DataResponse<Book> dataResponse = new DataResponse<>();
        try {
            final String token = request.getHeader("Authorization").substring(7);
            final String username = jwtService.extractUsername(token);
            dataResponse.setData(bookService.createBook(bookRequest, username));
            dataResponse.setMessage("Book has been created");
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            dataResponse.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        return ResponseEntity.status(response.getStatus()).body(dataResponse);
    }
}
