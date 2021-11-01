package dev.isaccanedo.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;
import dev.isaccanedo.urlshortener.repository.ShortUrlStatisticsRepository;
import dev.isaccanedo.urlshortener.service.ShortUrlStatisticsService;
import dev.isaccanedo.urlshortener.service.ShortUrlStatisticsServiceImpl;

/**
 * Unit tests for {@link ShortUrlStatisticsServiceImpl}
 * 
 * @see ShortUrlStatisticsService
 *
 */
public class ShortUrlStatisticsServiceImplTest {
	@Mock
	private ShortUrlStatisticsRepository shortUrlStatisticsRepository;
	
	private ShortUrlStatisticsService shortUrlStatisticsService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.shortUrlStatisticsService = new ShortUrlStatisticsServiceImpl(shortUrlStatisticsRepository);
	}
	
	@Test
	@Tag("UnitTest")
	public void incrementTotalAccess_should_increment_total_access_value_by_one () {
		int totalAccess = 1;
		long id = 1L;
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(id)
				.totalAccess(totalAccess)
				.build();
		
		doReturn(Optional.ofNullable(statistics)).when(shortUrlStatisticsRepository).findById(eq(id));
		
		shortUrlStatisticsService.incrementTotalAccessById(id);
		
		assertEquals(totalAccess + 1, statistics.getTotalAccess());
		
		verify(shortUrlStatisticsRepository, times(1)).findById(eq(id));
		verify(shortUrlStatisticsRepository, times(1)).save(eq(statistics));
	}
	
	@Test
	@Tag("UnitTest")
	public void createNewStatistics_should_return_new_initialized_statistics_data () {
		ArgumentCaptor<ShortUrlStatistics> argumentCaptor = ArgumentCaptor.forClass(ShortUrlStatistics.class);
		
		shortUrlStatisticsService.createNewStatistics();
		
		verify(shortUrlStatisticsRepository).save(argumentCaptor.capture());
		
		assertEquals(0L, argumentCaptor.getValue().getId());
		assertEquals(0L, argumentCaptor.getValue().getTotalAccess());
	}
}
