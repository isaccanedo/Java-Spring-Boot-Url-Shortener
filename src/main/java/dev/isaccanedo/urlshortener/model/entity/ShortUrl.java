package dev.isaccanedo.urlshortener.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dev.isaccanedo.urlshortener.generator.ShortUrlAliasGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Short URL representation of a long URL
 */
@Entity
@Table(name = "short_url")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl implements Serializable {
	private static final long serialVersionUID = 989434892875033570L;

	@Id
	private String alias;
	
	@Column(unique = true)
	private String longUrl;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@OneToOne
	private ShortUrlStatistics statistics;
	
	@ManyToOne
	private ShortUrlDomain domain;
	
	/**
	 * Gets the short URL's unique reference alias. 
	 * <p> This alias can be used to compose with the service domain host URL in order to solve the absolute short URL among different domains.
	 * <p>Note: The host should be resolved dynamically because of several instances of the same service that can be deployed on different domain servers (e.g. DNS, proxy, cloud, load balance, gateway, etc).
	 * <p>
	 * <p>Example:
	 * <p>
	 * <p>Long URL = https://www.example.com
	 * <p>Short URL alias = 123
	 * <p>Short URL Service host URL: http://service.com/
	 * <p>Absolute short URL: http://service.com/123
	 * <p>
	 * <p>
	 * @see ShortUrlAliasGenerator
	 * 
	 * @return short URL's unique alias
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * Gets the original long URL from this short URL
	 * <p>
	 * This is the original URL sent by client
	 * 
	 * @return long URL
	 */
	public String getLongUrl() {
		return longUrl;
	}
	
	/**
	 * Gets the short URL
	 * 
	 * <p>Note: The short URL address is resolved from its domain base URL
	 * <p>Example:
	 * <p>Domain URL: http://www.example.com/
	 * <p>Alias: 123abc
	 * <p>Short URL: http://www.example.com/123abc
	 * 
	 * @see ShortUrl#getDomain()
	 * @see ShortUrlDomain
	 * @see ShortUrlDomain#getUrlWithAliasPlaceholder()
	 * @see String#format(String, Object...)
	 * 
	 * @return short URL address resolved from the domain
	 */
	public String getShortUrl() {
		return Optional.ofNullable(getDomain()).map(ShortUrlDomain::getBaseUrl).orElse("/") + getAlias();
	}
	
	/**
	 * Gets the date of the moment when this original long URL was shortened
	 * 
	 * @return date of the moment when this original long URL was shortened
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Gets summary statistics about this short URL
	 * 
	 * @see ShortUrlStatistics
	 * 
	 * @return statistics about this short URL
	 */
	public ShortUrlStatistics getStatistics() {
		return statistics;
	}
}