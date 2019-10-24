package com.hcl.helathcare.service;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.helathcare.dto.LoginReqDto;
import com.hcl.helathcare.dto.LoginResDto;
import com.hcl.helathcare.entity.User;
import com.hcl.helathcare.exception.InvalidCredentialsException;
import com.hcl.helathcare.repository.UserRepository;
import com.hcl.helathcare.util.Constants;

/**
 * 
 * 
 * implenting Userservice and overriding all abstract method login()-check user
 * exits and validate with data is true return userId
 * 
 * @exception-InvalidCredentialsException
 *
 * @author Pradeep AJ
 */

@Service
public class LoginServiceImpl implements LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	/**
	 * method will check user with email and password if exists login success. else
	 * throw exception InvalidCredentialsException
	 * 
	 * @param LoginReqDto- @Valid -NotNull
	 * @return LoginResDto
	 * @exception InvalidCredentialsException
	 */

	@Override
	public LoginResDto login(@Valid LoginReqDto loginReqDto) throws InvalidCredentialsException {
		Optional<User> userExists = userRepository.findByEmail(loginReqDto.getEmail());
		if (userExists.isPresent()) {
			logger.info("Valid User::----------={}", loginReqDto.getEmail());
			if (userExists.get().getEmail().equals(loginReqDto.getEmail())
					&& userExists.get().getPassword().equals(loginReqDto.getPassword())) {



				logger.info("Valid Username and password::----------={}",loginReqDto.getEmail());
				return LoginResDto.builder().message(Constants.LOG_SUCCESS_MESSAGE)
						.statusCode(Constants.OK).userId(userExists.get().getUserId()).roleId(userExists.get().getRoleId()).build();
				

			}

		}
		throw new InvalidCredentialsException(Constants.INVALID_CREDENTIALS);

	}

}
