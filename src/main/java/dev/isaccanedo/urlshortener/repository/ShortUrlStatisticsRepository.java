package dev.isaccanedo.urlshortener.repository;

import org.springframework.data.repository.CrudRepository;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Repository for short URL statistics data persistence
 * 
 * @see ShortUrlStatistics
 */
public interface ShortUrlStatisticsRepository extends CrudRepository<ShortUrlStatistics, Long> {}