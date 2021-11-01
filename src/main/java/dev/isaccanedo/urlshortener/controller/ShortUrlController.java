package dev.isaccanedo.urlshortener.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.isaccanedo.urlshortener.exception.ShortUrlException;
import dev.isaccanedo.urlshortener.model.dto.ShortUrlDTO;
import dev.isaccanedo.urlshortener.model.dto.ShortUrlStatisticsDTO;
import dev.isaccanedo.urlshortener.model.entity.ShortUrl;
import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;
import dev.isaccanedo.urlshortener.service.ShortUrlService;
import dev.isaccanedo.urlshortener.service.ShortUrlStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
@Api(value="Short URL")
public class ShortUrlController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

	private ShortUrlService shortUrlService;
	private ShortUrlStatisticsService shortUrlStatisticsService;
	
	@Autowired
	public ShortUrlController(ShortUrlService shortUrlService, ShortUrlStatisticsService shortUrlStatisticsService) {
		super();
		this.shortUrlService = shortUrlService;
		this.shortUrlStatisticsService = shortUrlStatisticsService;
	}
	
	@PostMapping
	@ApiOperation(value = "Short a long URL", response = ShortUrlDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully shorted URL"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 400, message = "Invalid URL")
	})
	public ResponseEntity<ShortUrlDTO> shortUrl(@ApiParam("Long URL to be shorted") @RequestParam("longUrl") String longUrl) {
		LOGGER.info("Request: {} / longUrl = {}", HttpMethod.POST, longUrl);
		
		try {
			ShortUrl shortUrl = shortUrlService.shortUrl(longUrl);
			ShortUrlDTO dto = ShortUrlDTO.of(shortUrl);
			
			LOGGER.info("Response: {} {}", HttpStatus.OK, dto);
			return ResponseEntity.ok(dto);
		} catch (ShortUrlException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	@GetMapping("/{alias}")
	@Transactional
	@ApiOperation(value = "Redirects to original URL")
	@ApiResponses(value = {
	        @ApiResponse(code = 303, message = "Successfully redirected to original URL"),
	        @ApiResponse(code = 404, message = "Original URL not found"),
	        @ApiResponse(code = 400, message = "Invalid short URL alias")
	})
	public ResponseEntity<Object> redirectToOriginalUrl(@ApiParam("Short URL unique alias reference") @PathVariable("alias") String alias) {
		LOGGER.info("{} /{}", HttpMethod.GET, alias);
		
		Optional<ShortUrl> shortUrl = shortUrlService.getShortUrlByAlias(alias);
		
		if(shortUrl.isPresent()) {
			shortUrlStatisticsService.incrementTotalAccessById(shortUrl.get().getStatistics().getId());
			
			String longUrl = shortUrl.get().getLongUrl();
			LOGGER.info("Redirect to {}", longUrl);
			return ResponseEntity.status(HttpStatus.SEE_OTHER).header(HttpHeaders.LOCATION, longUrl).build();
		} else {
			LOGGER.info("Long URL not found");
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{alias}/statistics")
	@ApiOperation(value = "Get short URL statistics data", response = ShortUrlStatisticsDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully returned short URL statistics data"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 404, message = "Short URL not found"),
	})
	public ResponseEntity<ShortUrlStatisticsDTO> getShortUrlStatistics(@ApiParam("Short URL unique alias reference") @PathVariable("alias") String alias) {
		LOGGER.info("Request: {} /{}/statistics", HttpMethod.GET, alias);
		
		Optional<ShortUrl> shortUrl = shortUrlService.getShortUrlByAlias(alias);
		
		if(shortUrl.isPresent()) {
			ShortUrlStatistics statistics = shortUrl.get().getStatistics();
			ShortUrlStatisticsDTO dto = ShortUrlStatisticsDTO.of(statistics);
			
			LOGGER.info("Response: {} {}", HttpStatus.OK, dto);
			return ResponseEntity.ok().body(dto);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found");
		}
	}
}
