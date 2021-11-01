package dev.isaccanedo.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlDomain;

/**
 * Repository for short URL domain persistence
 * 
 * @see ShortUrlDomain
 */
public interface ShortUrlDomainRepository extends CrudRepository<ShortUrlDomain, Long> {
	Optional<ShortUrlDomain> findByName(String string);
}
