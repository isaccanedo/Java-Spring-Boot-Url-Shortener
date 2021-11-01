package dev.isaccanedo.urlshortener.service;

import dev.isaccanedo.urlshortener.exception.ShortUrlException;
import dev.isaccanedo.urlshortener.model.entity.ShortUrlDomain;

public interface ShortUrlDomainService {
	/**
	 * Gets the application default domain
	 * 
	 * @return application default domain
	 * 
	 * @throws ShortUrlException if domain not found
	 */
	ShortUrlDomain getDefaultDomain() throws ShortUrlException;
}
