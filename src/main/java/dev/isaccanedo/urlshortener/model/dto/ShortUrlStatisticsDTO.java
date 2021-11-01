package dev.isaccanedo.urlshortener.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dev.isaccanedo.urlshortener.model.entity.ShortUrlStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@ApiModel("ShortUrlStatisticsDTO")
public final class ShortUrlStatisticsDTO implements Serializable {
	private static final long serialVersionUID = -2734391922924226350L;

	@ApiModelProperty(notes = "How many times the short URL was accessed?")
	private final long totalAccess;
	
	@ApiModelProperty(notes = "When was the short URL last access?")
	private final Date lastAccess;
	
	public static ShortUrlStatisticsDTO of(ShortUrlStatistics statistics) {
		return ShortUrlStatisticsDTO.builder()
				.totalAccess(statistics.getTotalAccess())
				.lastAccess(statistics.getLastAccess())
				.build();
	}
}
