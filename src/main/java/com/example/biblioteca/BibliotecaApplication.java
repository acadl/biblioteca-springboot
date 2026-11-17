package com.example.biblioteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BibliotecaApplication {
	@Bean
	CommandLineRunner listarRunners(ApplicationContext ctx) {
		return args -> System.out.println(">>> Runners: " +
				Arrays.toString(ctx.getBeanNamesForType(CommandLineRunner.class)));
	}
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}
