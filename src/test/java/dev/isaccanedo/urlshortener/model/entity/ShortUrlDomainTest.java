package dev.isaccanedo.urlshortener.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlDomain;

/**
 * Unit tests of {@link ShortUrlDomain}
 * 
 * @see ShortUrlDomain
 */
public class ShortUrlDomainTest {
	@Test
	@Tag("UnitTest")
	public void test_no_args_constructor() {
		ShortUrlDomain domain = new ShortUrlDomain();
		
		String baseUrl = "http://localhost:8080";
		domain.setBaseUrl(baseUrl);
		
		long id = 1L;
		domain.setId(id);
		
		String name = "locahost";
		domain.setName(name);
		
		assertEquals(baseUrl, domain.getBaseUrl());
		assertEquals(id, domain.getId());
		assertEquals(name, domain.getName());
	}
	
	@Test
	@Tag("UnitTest")
	public void test_all_args_constructor() {
		String baseUrl = "http://localhost:8080";
		
		long id = 1L;
		
		String name = "locahost";
		
		ShortUrlDomain domain = new ShortUrlDomain(id, name, baseUrl);
		
		assertEquals(baseUrl, domain.getBaseUrl());
		assertEquals(id, domain.getId());
		assertEquals(name, domain.getName());
	}
	
	@Test
	@Tag("UnitTest")
	public void test_builder() {
		String baseUrl = "http://localhost:8080";
		
		long id = 1L;
		
		String name = "locahost";
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		assertEquals(baseUrl, domain.getBaseUrl());
		assertEquals(id, domain.getId());
		assertEquals(name, domain.getName());
	}
	
	@Test
	@Tag("UnitTest")
	public void test_toString() {
		String baseUrl = "http://localhost:8080";
		
		long id = 1L;
		
		String name = "locahost";
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		String expected = "ShortUrlDomain(id=" + id + ", name=" + name + ", baseUrl=" + baseUrl + ")";
		
		assertEquals(expected, domain.toString());
	}
	
	@Test
	@Tag("UnitTest")
	@SuppressWarnings("unlikely-arg-type")
	public void test_equals() {
		String baseUrl = "http://localhost:8080";
		
		long id = 1L;
		
		String name = "locahost";
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		ShortUrlDomain domain2 = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		ShortUrlDomain domain3 = ShortUrlDomain.builder()
				.baseUrl("123")
				.id(id)
				.name(name)
				.build();
		
		assertTrue(domain.equals(domain));
		assertTrue(domain.equals(domain2));
		
		assertFalse(domain.equals(null));
		assertFalse(domain.equals(new Exception()));
		assertFalse(domain.equals(domain3));
	}
	
	@Test
	@Tag("UnitTest")
	public void test_hashCode() {
		String baseUrl = "http://localhost:8080";
		
		long id = 1L;
		
		String name = "locahost";
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		int expected = -1963050824;
		
		assertEquals(expected, domain.hashCode());
	}
}
