package com.hcl.helathcare.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Pradeepa AJ
 *
 */

@Builder
@Getter
@Setter
public class ResponseDto {
	private int statusCode;
	private String message;

}
