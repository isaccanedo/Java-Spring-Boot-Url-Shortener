package dev.isaccanedo.urlshortener.service;

import java.util.Optional;

import dev.isaccanedo.urlshortener.exception.ShortUrlException;
import dev.isaccanedo.urlshortener.model.entity.ShortUrl;

/**
 * Service that provides URL shortener operations
 */
public interface ShortUrlService {	
	/**
	 * Short an long URL. Takes a long URL and squeezes into fewer characters to become easier to read, remember and share.
	 * 
	 * @param longUrl URL to be shortened
	 * 
	 * @return shortened URL
	 * 
	 * @throws ShortUrlException if the long URL is invalid or in case of some short URL resolution failure
	 * 
	 * @see ShortUrl
	 * @see ShortUrlException
	 */
	ShortUrl shortUrl(String longUrl) throws ShortUrlException;

	/**
	 * Gets a short URL by its unique alias
	 * 
	 * @param alias short URL's alias
	 * 
	 * @return short URL data of the given alias. {@link Optional#empty()} if not found.
	 */
	Optional<ShortUrl> getShortUrlByAlias(String alias);
}
