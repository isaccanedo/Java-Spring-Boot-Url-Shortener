package dev.isaccanedo.urlshortener.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan( basePackages = {
		"dev.isaccanedo.urlshortener.application",
		"dev.isaccanedo.urlshortener.controller",
		"dev.isaccanedo.urlshortener.exception",
		"dev.isaccanedo.urlshortener.model.dto",
		"dev.isaccanedo.urlshortener.model.entity",
		"dev.isaccanedo.urlshortener.repository",
		"dev.isaccanedo.urlshortener.config",
		"dev.isaccanedo.urlshortener.logger",
		"dev.isaccanedo.urlshortener.service",
		"dev.isaccanedo.urlshortener.validator"
		})
@EntityScan(basePackages = "dev.isaccanedo.urlshortener.model.entity")
@EnableJpaRepositories("dev.isaccanedo.urlshortener.repository")
@PropertySource(value = "classpath:application.properties")
public class UrlShortenerApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UrlShortenerApplication.class);
	}
}
