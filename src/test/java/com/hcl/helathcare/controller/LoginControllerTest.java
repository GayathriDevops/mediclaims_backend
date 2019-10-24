package com.hcl.helathcare.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.helathcare.dto.LoginReqDto;
import com.hcl.helathcare.dto.LoginResDto;
import com.hcl.helathcare.exception.InvalidCredentialsException;
import com.hcl.helathcare.service.LoginService;
import com.hcl.helathcare.util.Constants;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class LoginControllerTest {

	@Mock
	 LoginService loginService;
	@InjectMocks
	 LoginController loginController;
	
	 LoginReqDto loginReq;
 LoginResDto loginRes; 
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		
		loginReq = LoginReqDto.builder().email("Pradeep.aj28@gmail.com").password("Pradeep").build();
		loginRes = LoginResDto.builder().message(Constants.LOG_SUCCESS_MESSAGE).statusCode(Constants.OK).userId(1L).roleId(1L)
				.build();
		
		
	}
	@Test
	public void loginTest() throws InvalidCredentialsException {
		Mockito.when(loginService.login(loginReq)).thenReturn(loginRes);
		ResponseEntity<LoginResDto> actRes=loginController.login(loginReq);
		assertEquals(HttpStatus.OK, actRes.getStatusCode());
		
	}

}

