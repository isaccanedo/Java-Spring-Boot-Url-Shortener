package dev.isaccanedo.urlshortener.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@ApiModel("ApiErrorDTO")
public final class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = 3815698689078918046L;

	@ApiModelProperty(notes = "HTTP status code of the error")
	private final int status;
	
	@ApiModelProperty(notes = "User-friendly message about the error")
	private final String message;

	@ApiModelProperty(notes = "Technical description about the error")
	private final String error;
	
	@ApiModelProperty(notes = "Date timestamp of the moment when the error happend")
	private final Date timestamp;
}
