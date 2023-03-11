package com.lazyorchest.elibrary.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazyorchest.elibrary.dto.DataResponse;
import com.lazyorchest.elibrary.dto.LoginRequest;
import com.lazyorchest.elibrary.dto.RegisterRequest;
import com.lazyorchest.elibrary.dto.RegisterResponse;
import com.lazyorchest.elibrary.models.Role;
import com.lazyorchest.elibrary.models.User;
import com.lazyorchest.elibrary.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @Operation(summary = "Register user", description = "This is endpoint to register new admin", tags = "User Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin has been created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @SecurityRequirements
    @PostMapping("/auth/register/admin")
    public ResponseEntity<DataResponse<RegisterResponse>> registerAdmin(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        DataResponse<RegisterResponse> dataResponse = new DataResponse<>();
        try {
            registerRequest.setRole(Role.ADMIN);
            User user = authService.register(registerRequest);
            dataResponse.setData(RegisterResponse.builder().name(user.getName()).username(user.getUsername()).build());
            dataResponse.setMessage("User has been created");
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            dataResponse.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        return ResponseEntity.status(response.getStatus()).body(dataResponse);
    }

    @Operation(summary = "Register user", description = "This is endpoint to register new user", tags = "User Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has been created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @SecurityRequirements
    @PostMapping("/auth/register")
    public ResponseEntity<DataResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) throws IOException {
        DataResponse<RegisterResponse> dataResponse = new DataResponse<>();
        try {
            User user = authService.register(registerRequest);
            dataResponse.setData(RegisterResponse.builder().name(user.getName()).username(user.getUsername()).build());
            dataResponse.setMessage("User has been created");
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            dataResponse.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        return ResponseEntity.status(response.getStatus()).body(dataResponse);
    }

    @Operation(summary = "Login user", description = "This is endpoint to login registered user", tags = "User Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been logged in", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @SecurityRequirements
    @PostMapping("/auth/login")
    public ResponseEntity<DataResponse<String>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        DataResponse<String> dataResponse = new DataResponse<>();
        try {
            String token = authService.login(loginRequest);
            dataResponse.setData(token);
            dataResponse.setMessage("Token created");
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            dataResponse.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        return ResponseEntity.status(response.getStatus()).body(dataResponse);
    }
}
