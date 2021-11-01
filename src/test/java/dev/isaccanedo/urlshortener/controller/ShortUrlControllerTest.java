package dev.isaccanedo.urlshortener.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Date;
import java.util.Optional;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import dev.isaccanedo.urlshortener.controller.ShortUrlController;
import dev.isaccanedo.urlshortener.exception.ShortUrlException;
import dev.isaccanedo.urlshortener.model.dto.ShortUrlDTO;
import dev.isaccanedo.urlshortener.model.dto.ShortUrlStatisticsDTO;
import dev.isaccanedo.urlshortener.model.entity.ShortUrl;
import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;
import dev.isaccanedo.urlshortener.service.ShortUrlService;
import dev.isaccanedo.urlshortener.service.ShortUrlStatisticsService;

/**
 * Unit tests for {@link ShortUrlController}
 * 
 * @see ShortUrlController
 *
 */
public class ShortUrlControllerTest {
	@Mock
	private ShortUrlService shortUrlService;
	
	@Mock
	private ShortUrlStatisticsService shortUrlStatisticsService;
	
	private ShortUrlController shortUrlController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.shortUrlController = new ShortUrlController(shortUrlService, shortUrlStatisticsService);
	}
	
	@Test
	@Tag("UnitTest")
	public void shortUrl_when_invalid_url_should_return_bad_request_error() throws ShortUrlException {
		String longUrl = "23xxxxx";
		
		ShortUrlException exception = new ShortUrlException("invalid URL");
		doThrow(exception).when(shortUrlService).shortUrl(eq(longUrl));
		
		try {
			shortUrlController.shortUrl(longUrl);
			fail("should throw ResponseStatusException");
		} catch (ResponseStatusException e) {
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
			assertEquals(exception, e.getCause());
			verify(shortUrlService, times(1)).shortUrl(eq(longUrl));
		}
	}
	
	@Test
	@Tag("UnitTest")
	public void shortUrl_when_valid_url_should_return_short_url() throws ShortUrlException {
		String longUrl = "http://www.example.com";
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(0L)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias("test")
				.creationDate(new Date())
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		ShortUrlDTO dto = ShortUrlDTO.of(shortUrl);
		
		doReturn(shortUrl).when(shortUrlService).shortUrl(eq(longUrl));
		
		ResponseEntity<ShortUrlDTO> response = shortUrlController.shortUrl(longUrl);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(dto, response.getBody());
		
		verify(shortUrlService, times(1)).shortUrl(eq(longUrl));
	}
	
	@Test
	@Tag("UnitTest")
	public void redirectToOriginalUrl_when_alias_does_not_exist_should_return_not_found_error () {
		String shortUrlAlias = "xxxxxxxxxx";
		
		doReturn(Optional.empty()).when(shortUrlService).getShortUrlByAlias(eq(shortUrlAlias));
		
		ResponseEntity<Object> response = shortUrlController.redirectToOriginalUrl(shortUrlAlias);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		verify(shortUrlService, times(1)).getShortUrlByAlias(eq(shortUrlAlias));
		verifyNoInteractions(shortUrlStatisticsService);
	}
	
	@Test
	@Tag("UnitTest")
	public void redirectToOriginalUrl_when_alias_does_exist_should_redirect_to_long_url() {
		String shortUrlAlias = "123123";
		String originalUrl = "http://www.google.com.br";
		Date creationDate = new Date();
		long id = 1L;
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(id)
				.totalAccess(0)
				.build();
				
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(shortUrlAlias)
				.creationDate(creationDate)
				.longUrl(originalUrl)
				.statistics(statistics)
				.build();
		
		doReturn(Optional.ofNullable(shortUrl)).when(shortUrlService).getShortUrlByAlias(eq(shortUrlAlias));
		
		ResponseEntity<Object> response = shortUrlController.redirectToOriginalUrl(shortUrlAlias);
		
		assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
		assertEquals(originalUrl, response.getHeaders().get(HttpHeaders.LOCATION).get(0));
		
		verify(shortUrlService, times(1)).getShortUrlByAlias(eq(shortUrlAlias));
		verify(shortUrlStatisticsService, times(1)).incrementTotalAccessById(eq(id));
	}
	
	@Test
	@Tag("UnitTest")
	public void getShortUrlStatistics_when_invalid_alias_should_return_not_found_error () {
		String alias = "123123";
		
		doReturn(Optional.empty()).when(shortUrlService).getShortUrlByAlias(eq(alias));
		
		try {
			shortUrlController.getShortUrlStatistics(alias);
			fail("should throw ResponseStatusException");
		} catch (ResponseStatusException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals("404 NOT_FOUND \"Short URL not found\"", e.getMostSpecificCause().getMessage());
			verify(shortUrlService, times(1)).getShortUrlByAlias(eq(alias));
		}
	}
	
	@Test
	@Tag("UnitTest")
	public void getShortUrlStatistics_when_valid_alias_should_return_short_url_statistics () {
		String alias = "123123";
		String originalUrl = "http://www.google.com.br";
		Date creationDate = new Date();
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(0)
				.build();
				
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(originalUrl)
				.statistics(statistics)
				.build();
		
		ShortUrlStatisticsDTO dto = ShortUrlStatisticsDTO.of(statistics);
		
		doReturn(Optional.ofNullable(shortUrl)).when(shortUrlService).getShortUrlByAlias(eq(alias));
		
		ResponseEntity<ShortUrlStatisticsDTO> response = shortUrlController.getShortUrlStatistics(alias);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(dto, response.getBody());
		
		verify(shortUrlService, times(1)).getShortUrlByAlias(eq(alias));
	}
}
