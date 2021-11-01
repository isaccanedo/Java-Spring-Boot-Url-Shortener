package dev.isaccanedo.urlshortener.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;
import dev.isaccanedo.urlshortener.repository.ShortUrlStatisticsRepository;

@Service
public class ShortUrlStatisticsServiceImpl implements ShortUrlStatisticsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlStatisticsServiceImpl.class);
	
	private ShortUrlStatisticsRepository shortUrlStatisticsRepository;
	
	@Autowired
	public ShortUrlStatisticsServiceImpl(ShortUrlStatisticsRepository shortUrlStatisticsRepository) {
		super();
		this.shortUrlStatisticsRepository = shortUrlStatisticsRepository;
	}

	/**
	 * Updates short URL statistics data:
	 * 
	 * <p> Increment total access by one
	 * <p> Updates last access date with current date
	 * <p> Note: This method runs asynchronously so that the user doesn't need to wait statistics data being computed (this can be checked later by another request) and instead be redirected to long URL faster.
	 * 
	 * @param id short URL statistics unique id reference
	 */
	@Async
	@Override
	public void incrementTotalAccessById(long id) {
		LOGGER.info("start async incrementTotalAccessById({})", id);
		
		shortUrlStatisticsRepository.findById(id).ifPresent(s -> {
			s.setTotalAccess(s.getTotalAccess() + 1);
			s.setLastAccess(new Date());
			shortUrlStatisticsRepository.save(s);
		});
		
		LOGGER.info("finished async incrementTotalAccessById({})", id);
	}

	@Override
	public ShortUrlStatistics createNewStatistics() {
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.totalAccess(0L)
				.build();
	
		return shortUrlStatisticsRepository.save(statistics);
	}
}
