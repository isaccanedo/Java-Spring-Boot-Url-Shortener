package dev.isaccanedo.urlshortener.exception;

/**
 * Exceptions for related to short URL failures
 */
public class ShortUrlException extends Exception {
	private static final long serialVersionUID = -969159821708530042L;

	public ShortUrlException(String message) {
		super(message);
	}
	
	public ShortUrlException(Throwable cause) {
		super(cause);
	}
	
	public ShortUrlException(String message, Throwable cause) {
		super(message, cause);
	}
}
