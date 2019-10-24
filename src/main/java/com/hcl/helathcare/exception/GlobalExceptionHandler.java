package com.hcl.helathcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hcl.helathcare.dto.ResponseDto;

/**
 * 
 * @author Pradeepa AJ Globly handling the exception using
 *         {@Controlleradvice and extending ResponseEntityExceptionHandler to
 *         give all checked and unchecked exception in JSON response }
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 * @param exception- handling checked and unchecked exception
	 * @param request-   API request
	 * @return ResponseDto- message and status code
	 */

	@ExceptionHandler(Exception.class)

	public ResponseEntity<ResponseDto> globalExceptionHandler(Exception exception, WebRequest request) {

		return new ResponseEntity<>(
				ResponseDto.builder().message(exception.getMessage()).statusCode(HttpStatus.NOT_FOUND.value()).build(),
				HttpStatus.NOT_FOUND);

	}

	/**
	 * 
<<<<<<< HEAD
	 * @param InvalidCredentialsException- unchecked exception
	 * @param request-                     API request
=======
	 * @param InvalidCredentialsException- checked and unchecked exception
	 * @param request- API request
>>>>>>> 259837899ac0190e8421ae4add693ec13275c33d
	 * @return ResponseDto- message and status code
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ResponseDto> invalidCredentialsExceptionHandler(InvalidCredentialsException ex,
			WebRequest request) {
		ResponseDto responseDto = ResponseDto.builder().message(ex.getMessage())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).build();
		return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(UserNotExistsException.class)
	public ResponseEntity<ResponseDto> userNotExistsExceptionHandler(UserNotExistsException ex, WebRequest request) {
		ResponseDto responseDto = ResponseDto.builder().message(ex.getMessage())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).build();
		return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(PolicyNotExistsException.class)
	public ResponseEntity<ResponseDto> policyNotExistsException(PolicyNotExistsException ex, WebRequest request) {
		ResponseDto responseDto = ResponseDto.builder().message(ex.getMessage())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).build();
		return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(InvalidClaimAmountException.class)
	public ResponseEntity<ResponseDto> fileStorageExceptionHandler(InvalidClaimAmountException ex, WebRequest request) {
		ResponseDto responseDto = ResponseDto.builder().message(ex.getMessage())
				.statusCode(HttpStatus.BAD_REQUEST.value()).build();
		return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NoPolicyNotFound.class)
	public ResponseEntity<ResponseDto> noPolicyNotFound(NoPolicyNotFound ex, WebRequest request) {
		ResponseDto responseDto = ResponseDto.builder().message(ex.getMessage())
				.statusCode(HttpStatus.NOT_FOUND.value()).build();
		return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ClaimNotPresentException.class)
	public ResponseEntity<ResponseDto> claimNotPresentException(ClaimNotPresentException ex, WebRequest request) {
		ResponseDto responseDto = ResponseDto.builder().message(ex.getMessage())
				.statusCode(HttpStatus.NOT_FOUND.value()).build();
		return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);

	}

}