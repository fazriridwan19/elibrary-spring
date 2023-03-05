package com.lazyorchest.elibrary;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		type = SecuritySchemeType.HTTP,
		name = "Bearer_Authentication",
		bearerFormat = "JWT",
		scheme = "bearer"
)
@OpenAPIDefinition(info = @Info(title = "E-Commerce API", version = "1.0.0"), security = { @SecurityRequirement(name = "Bearer_Authentication") })
public class ElibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElibraryApplication.class, args);
	}

}
