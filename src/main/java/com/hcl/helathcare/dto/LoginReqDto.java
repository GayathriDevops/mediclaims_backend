package com.hcl.helathcare.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginReqDto {

	private String email;

	private String password;

}
