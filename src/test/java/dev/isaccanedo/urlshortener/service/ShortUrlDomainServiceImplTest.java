package dev.isaccanedo.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import dev.isaccanedo.urlshortener.exception.ShortUrlException;
import dev.isaccanedo.urlshortener.model.entity.ShortUrlDomain;
import dev.isaccanedo.urlshortener.repository.ShortUrlDomainRepository;
import dev.isaccanedo.urlshortener.service.ShortUrlDomainService;
import dev.isaccanedo.urlshortener.service.ShortUrlDomainServiceImpl;

/**
 * Unit tests of {@link ShortUrlDomainServiceImpl}
 * 
 * @see ShortUrlDomainServiceImpl
 *
 */
public class ShortUrlDomainServiceImplTest {
	@Mock
	private Environment environment;
	
	@Mock
	private ShortUrlDomainRepository shortUrlDomainRepository;
	
	private ShortUrlDomainService shortUrlDomainService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.shortUrlDomainService = new ShortUrlDomainServiceImpl(environment, shortUrlDomainRepository);
	}
	
	@Test
	@Tag("UnitTest")
	public void getDefaultDomain_when_not_found_should_create_and_return () throws ShortUrlException {
		String name = "default";
		long id = 1L;
		String baseUrl = "http://localhost:8080/";
		String propertyKey = "application.domain.base-url";
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		doReturn(Optional.empty()).when(shortUrlDomainRepository).findByName(eq(name));
		doReturn(baseUrl).when(environment).getProperty(eq(propertyKey));
		doReturn(domain).when(shortUrlDomainRepository).save(any(ShortUrlDomain.class));
		
		ShortUrlDomain defaultDomain = shortUrlDomainService.getDefaultDomain();
		
		assertEquals(baseUrl, defaultDomain.getBaseUrl());
		assertEquals(name, defaultDomain.getName());
		
		verify(shortUrlDomainRepository, times(1)).findByName(eq(name));
		verify(environment, times(1)).getProperty(eq(propertyKey));
		verify(shortUrlDomainRepository, times(1)).save(any(ShortUrlDomain.class));
	}
	
	@Test
	@Tag("UnitTest")
	public void getDefaultDomain_when_found_should_return () throws ShortUrlException {
		String name = "default";
		long id = 1L;
		String baseUrl = "http://localhost:8080/";
		String propertyKey = "application.domain.base-url";
		
		ShortUrlDomain domain = ShortUrlDomain.builder()
				.baseUrl(baseUrl)
				.id(id)
				.name(name)
				.build();
		
		doReturn(Optional.ofNullable(domain)).when(shortUrlDomainRepository).findByName(eq(name));
		doReturn(baseUrl).when(environment).getProperty(eq(propertyKey));
		doReturn(domain).when(shortUrlDomainRepository).save(any(ShortUrlDomain.class));
		
		ShortUrlDomain defaultDomain = shortUrlDomainService.getDefaultDomain();
		
		assertEquals(baseUrl, defaultDomain.getBaseUrl());
		assertEquals(name, defaultDomain.getName());
		
		verify(shortUrlDomainRepository, times(1)).findByName(eq(name));
		verifyNoInteractions(environment);
		verifyNoMoreInteractions(shortUrlDomainRepository);
	}
}
