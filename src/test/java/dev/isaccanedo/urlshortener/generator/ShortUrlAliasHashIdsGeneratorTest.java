package dev.isaccanedo.urlshortener.generator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.isaccanedo.urlshortener.generator.ShortUrlAliasGenerator;
import dev.isaccanedo.urlshortener.generator.ShortUrlAliasHashIdsGenerator;

public class ShortUrlAliasHashIdsGeneratorTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlAliasHashIdsGeneratorTest.class);
	
	private ShortUrlAliasGenerator shortUrlAliasGenerator;
	
	@BeforeEach
	public void setup() {
		this.shortUrlAliasGenerator = new ShortUrlAliasHashIdsGenerator();
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_when_long_URL_is_null () {
		String alias = shortUrlAliasGenerator.generate(null);
		assertNotNull(alias);
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_when_long_URL_is_empty () {
		String alias = shortUrlAliasGenerator.generate("");
		assertNotNull(alias);
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_when_long_URL_is_blank () {
		String alias = shortUrlAliasGenerator.generate("   ");
		assertNotNull(alias);
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_when_long_URL_is_valid_should_return_generated_alias_hash() {
		String longUrl = "http://www.example.com";
		
		String alias = shortUrlAliasGenerator.generate(longUrl);
		
		assertEquals("KLCLIx", alias);
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_test_idempotence_should_always_generate_same_alias_for_same_url () {
		String url = "http://www.google.com";
		
		String alias = shortUrlAliasGenerator.generate(url);
		
		for(int i = 0; i < 10000; i++) {
			String alias2 = shortUrlAliasGenerator.generate(url);
			assertEquals(alias, alias2);
		}
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_test_uniqueness_given_long_list_of_urls_should_generate_distict_alias_for_each_url() throws IOException {
		Set<String> longUrls = new HashSet<>();
		List<String> aliases = new ArrayList<>();
		
		Files.lines(Paths.get("src/test/resources/long-url-list.txt")).forEach(url -> {
			longUrls.add(url);
			String alias = shortUrlAliasGenerator.generate(url);
			
			LOGGER.info("{} -> {}", alias, url);
			
			assertFalse("Same alias " + alias + " from different URLs", aliases.contains(alias));
			aliases.add(alias);
		});

		assertEquals(longUrls.size(), aliases.size());
	}
}
