package dev.isaccanedo.urlshortener.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Unit tests for {@link ShortUrlStatistics}
 * 
 * @author ShortUrlStatistics
 *
 */
public class ShortUrlStatisticsTest {
	@Test
	@Tag("UnitTest")
	public void testNoArgsConstructor() {
		ShortUrlStatistics shortUrlStatistics = new ShortUrlStatistics();
		
		long id = 0L;
		shortUrlStatistics.setId(id);
		
		long totalAccess = 0L;
		shortUrlStatistics.setTotalAccess(totalAccess);
		
		assertEquals(id, shortUrlStatistics.getId());
		assertEquals(totalAccess, shortUrlStatistics.getTotalAccess());
	}
}
