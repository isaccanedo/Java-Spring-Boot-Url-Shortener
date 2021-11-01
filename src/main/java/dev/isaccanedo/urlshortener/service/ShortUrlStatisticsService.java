package dev.isaccanedo.urlshortener.service;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Service with operations related to short URL's statistics
 */
public interface ShortUrlStatisticsService {
	/**
	 * Increments total access by one
	 * Updates last access date with current date
	 * 
	 * @param id statistics unique id
	 */
	void incrementTotalAccessById(long id);
	
	/**
	 * Creates new initialized statistics data for a new short URL
	 * 
	 * @return initialized statistics for a new short URL
	 */
	ShortUrlStatistics createNewStatistics();
}
