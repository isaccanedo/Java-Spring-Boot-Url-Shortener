package dev.isaccanedo.urlshortener.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.isaccanedo.urlshortener.exception.ShortUrlException;

/**
 * Unit test of {@link ShortUrlException}
 * 
 * @see ShortUrlException
 *
 */
public class ShortUrlExceptionTest {
	@Test
	@Tag("UnitTest")
	public void messageArgConstructor() {
		String message = "testing exception";
		
		ShortUrlException exception = new ShortUrlException(message);
		
		assertEquals(message, exception.getMessage());
	}
	
	@Test
	@Tag("UnitTest")
	public void causeArgConstructor() {
		Exception cause = new Exception();
		
		ShortUrlException exception = new ShortUrlException(cause);
		
		assertEquals(cause, exception.getCause());
	}
	
	@Test
	@Tag("UnitTest")
	public void messageAndCauseArgsConstructor() {
		String message = "testing exception";
		Exception cause = new Exception();
		
		ShortUrlException exception = new ShortUrlException(message, cause);
		
		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}
}
