package dev.isaccanedo.urlshortener.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.isaccanedo.urlshortener.model.entity.ShortUrl;
import dev.isaccanedo.urlshortener.model.entity.ShortUrlDomain;
import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Unit test of {@link ShortUrl}
 * 
 * @see ShortUrl
 *
 */
public class ShortUrlTest {
	@Test
	@Tag("UnitTest")
	public void testNoArgsConstructor() {
		ShortUrl shortUrl = new ShortUrl();
		
		String alias = "123";
		shortUrl.setAlias(alias);
		
		Date creationDate = new Date();
		shortUrl.setCreationDate(creationDate);
		
		String longUrl = "http://www.example.com";
		shortUrl.setLongUrl(longUrl);
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		shortUrl.setStatistics(statistics);
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl("http://localhost:8080")
				.id(1L)
				.build();
		
		shortUrl.setDomain(domain);
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(creationDate, shortUrl.getCreationDate());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(statistics, shortUrl.getStatistics());
		assertEquals(domain, shortUrl.getDomain());
	}
	
	@Test
	@Tag("UnitTest")
	public void testAllArgsConstructor() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl("http://localhost:8080")
				.id(1L)
				.name("localhost")
				.build();
		
		ShortUrl shortUrl = new ShortUrl(alias, longUrl, creationDate, statistics, domain);
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(creationDate, shortUrl.getCreationDate());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(statistics, shortUrl.getStatistics());
		assertEquals(domain, shortUrl.getDomain());
	}
	
	@Test
	@Tag("UnitTest")
	public void testBuilder() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl("http://localhost:8080")
				.id(1L)
				.name("localhost")
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.domain(domain)
				.build();
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(creationDate, shortUrl.getCreationDate());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(statistics, shortUrl.getStatistics());
		assertEquals(domain, shortUrl.getDomain());
	}
	
	@Test
	@Tag("UnitTest")
	public void testEquals() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl("http://localhost:8080")
				.id(1L)
				.name("localhost")
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.domain(domain)
				.build();
		
		assertTrue(shortUrl.equals(shortUrl));

		assertFalse(shortUrl.equals(null));
	}
	
	@Test
	@Tag("UnitTest")
	public void testHashCode() {
		String alias = "123";
		String longUrl = "http://www.example.com";
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl("http://localhost:8080")
				.id(1L)
				.name("localhost")
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.longUrl(longUrl)
				.statistics(statistics)
				.domain(domain)
				.build();
		
		int expected = -82892984;
		
		assertEquals(expected, shortUrl.hashCode());
	}
	
	@Test
	@Tag("UnitTest")
	public void testToString() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl("http://localhost:8080")
				.id(1L)
				.name("localhost")
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.domain(domain)
				.statistics(statistics)
				.build();
		
		String expected = "ShortUrl(alias="+alias+", longUrl="+longUrl+", creationDate="+creationDate+", statistics="+statistics+", domain="+domain+")";
		
		assertEquals(expected, shortUrl.toString());
	}
}
