package com.hcl.helathcare.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hcl.helathcare.dto.LoginReqDto;
import com.hcl.helathcare.dto.LoginResDto;
import com.hcl.helathcare.entity.User;
import com.hcl.helathcare.exception.InvalidCredentialsException;
import com.hcl.helathcare.repository.UserRepository;
import com.hcl.helathcare.util.Constants;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class LoginServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private LoginServiceImpl loginServiceImpl;
	
	 public LoginReqDto loginReq;
	 public LoginResDto loginRes; 
	 public User user;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	 user=new User();
		user.setUserId(1L);
		user.setEmail("Pradeep.aj28@gmail.com");
		user.setPassword("Pradeep");
		loginReq = LoginReqDto.builder().email("Pradeep.aj28@gmail.com").password("Pradeep").build();
		loginRes = LoginResDto.builder().message(Constants.LOG_SUCCESS_MESSAGE).statusCode(Constants.OK).userId(1L).roleId(1L)
				.build();
		
		
	}

	@Test
	public void loginTest() throws InvalidCredentialsException {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		LoginResDto actualValue = loginServiceImpl.login(loginReq);
		assertEquals(loginRes.getStatusCode(), actualValue.getStatusCode());
	}

}
