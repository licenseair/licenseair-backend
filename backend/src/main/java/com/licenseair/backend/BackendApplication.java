package com.licenseair.backend;

import com.licenseair.backend.domain.Application;
import com.licenseair.backend.domain.Category;
import io.ebean.ExpressionList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
