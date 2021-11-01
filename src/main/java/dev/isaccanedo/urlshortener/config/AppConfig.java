package dev.isaccanedo.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.isaccanedo.urlshortener.generator.ShortUrlAliasGenerator;
import dev.isaccanedo.urlshortener.generator.ShortUrlAliasHashIdsGenerator;

/**
 * Application general configurations
 */
@Configuration
public class AppConfig {
	@Bean
	public ShortUrlAliasGenerator shortUrlAliasGenerator() {
		return new ShortUrlAliasHashIdsGenerator();
	}
}
