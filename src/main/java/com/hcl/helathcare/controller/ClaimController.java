package com.hcl.helathcare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.dto.UpdateResponseDTO;
import com.hcl.helathcare.dto.ViewClaimsDTO;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.PolicyNotExistsException;
import com.hcl.helathcare.service.ClaimService;
import com.hcl.helathcare.util.Constants;

/**
 * ClaimController implements methods for creating, viewing and updating claims
 * Only admin can view and update claims
 * 
 * 
**/
@RestController
@CrossOrigin(allowedHeaders = { "*", "/" }, origins = { "*", "/" })
public class ClaimController {
	private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);

	@Autowired
	ClaimService claimService;

	
	/**
	 * @param roleName
	 * @return ViewClaimsDTO
	 * @throws ClaimNotPresentException
	 * @author sravya
	 */
	@GetMapping("/admin/claims/{roleName}")
	public ResponseEntity<ViewClaimsDTO> viewClaims(@PathVariable String roleName) throws ClaimNotPresentException {

		logger.info("::Enter into------------: viewClaims()");

		List<ClaimResponseDTO> viewClaims = claimService.viewClaims(roleName);

		ViewClaimsDTO claimsList = new ViewClaimsDTO();
		claimsList.setViewClaims(viewClaims);

		return new ResponseEntity<>(claimsList, HttpStatus.OK);
	}
	
	/**
	 * method update claims returns list of all claims
	 * @param updateRequest
	 * @return UpdateResponseDTO
	 * @throws PolicyNotExistsException
	 * @throws ClaimNotPresentException
	 * @author sravya
	 */
	@PostMapping("/admin/claims")
	public ResponseEntity<UpdateResponseDTO> updateClaims(@RequestBody UpdateRequestDTO updateRequest)
			throws PolicyNotExistsException,ClaimNotPresentException {

		logger.info("::Enter into------------: updateClaims()");

		claimService.updateClaims(updateRequest);

		UpdateResponseDTO response = new UpdateResponseDTO();
		response.setMessage(Constants.UPDATE_SUCCESS_MESSAGE);
		response.setStatusCode(Constants.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}


	
}
