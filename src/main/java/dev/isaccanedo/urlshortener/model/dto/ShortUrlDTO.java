package dev.isaccanedo.urlshortener.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dev.isaccanedo.urlshortener.model.entity.ShortUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@ApiModel("ShortUrlDTO")
public final class ShortUrlDTO implements Serializable {
	private static final long serialVersionUID = -1349703406800640400L;

	@ApiModelProperty(notes = "Short URL unique alias reference", readOnly = true, example = "abc123", allowEmptyValue = false, position = 0, required = true)
	private final String alias;
	
	@ApiModelProperty(notes = "Short URL address from domain and alias (use this to be redirected to the original long URL)", readOnly = true, example = "http://www.service.com/abc123", allowEmptyValue = false, position = 0, required = true)
	private final String shortUrl;
	
	@ApiModelProperty(notes = "Original long URL used to generate the short URL", readOnly = true, example = "http://www.example.com/url/to/short", allowEmptyValue = false, position = 0, required = true)
	private final String longUrl;
	
	@ApiModelProperty(notes = "Date of the moment when the short URL was created")
	private final String creationDate;
	
	public static ShortUrlDTO of(ShortUrl shortUrl) {
		return ShortUrlDTO.builder()
				.alias(shortUrl.getAlias())
				.shortUrl(shortUrl.getShortUrl())
				.longUrl(shortUrl.getLongUrl())
				.creationDate(shortUrl.getCreationDate().toString())
				.build();
	}
}
