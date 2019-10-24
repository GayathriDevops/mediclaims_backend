package com.hcl.helathcare.dto;

import lombok.Builder;

/**
 * 
 * @author Pradeepa AJ
 *
 */
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResDto {
	private Long userId;
	private int statusCode;
	private String message;
	private Long roleId;
}
