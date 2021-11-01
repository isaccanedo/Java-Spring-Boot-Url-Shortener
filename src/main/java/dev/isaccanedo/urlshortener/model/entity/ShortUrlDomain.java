package dev.isaccanedo.urlshortener.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Maps a domain server where a short URL belongs to.
 * 
 * This helps resolve short URL
 * <p>Example: 
 * <p> long URL: http://www.long-url.com
 * <p> short URL alias: abc123
 * <p> domain base URL: http://www.example.com
 * <p> Resolved short URL from domain: http://www.example.com/abc123
 * <p> So whenever the user access http://www.example.com/abc123 then it should be redirected to http://www.long-url.com
 */
@Entity
@Table(name = "short_url_domain")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDomain implements Serializable {
	private static final long serialVersionUID = -8174644323309134095L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String baseUrl;
	
	/**
	 * Gets the domain name identifier.
	 * <p>
	 * This can be used as an identification alias of the domain.
	 * 
	 * @return domain name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the domain base URL
	 * <p>
	 * This is useful to resolve short URL with alias
	 * <p>
	 * Example:
	 * <p> short URL alias: abc123
	 * <p> domain base URL: http://www.example.com
	 * <p> Short URL: http://www.example.com/abc123
	 * 
	 * @return domain base URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
}
