package dev.isaccanedo.urlshortener.model.entity;

import java.io.Serializable;
import java.util.Date;

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

/**
 * Summary statistics about a short URL, such as total access, last access date, etc.
 */
@Entity
@Table(name = "short_url_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlStatistics implements Serializable {
	private static final long serialVersionUID = -702947345942970404L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private long totalAccess;
	
	@Column
	private Date lastAccess;
	
	/**
	 * Gets the total number of times the short URL was accessed
	 * 
	 * @return the total number of times the short URL was accessed
	 */
	public long getTotalAccess() {
		return totalAccess;
	}
	
	/**
	 * Gets the date of the last access
	 * 
	 * @return last access date. {@code null} if never accessed before.
	 */
	public Date getLastAccess() {
		return lastAccess;
	}
}
