package com.hcl.helathcare.service;

import javax.validation.Valid;

import com.hcl.helathcare.dto.LoginReqDto;
import com.hcl.helathcare.dto.LoginResDto;
import com.hcl.helathcare.exception.InvalidCredentialsException;

public interface LoginService {

	LoginResDto login(@Valid LoginReqDto loginReqDto)throws InvalidCredentialsException;

}
