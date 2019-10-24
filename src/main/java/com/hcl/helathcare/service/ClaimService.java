package com.hcl.helathcare.service;

import java.util.List;

import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.PolicyNotExistsException;

public interface ClaimService {

	/**
	 * @param updateRequest
	 * @return
	 * @throws ClaimNotPresentException
	 * @throws PolicyNotExistsException
	 */
	

	List<ClaimResponseDTO> viewClaims(String roleName) throws ClaimNotPresentException;
	

	Claim updateClaims(UpdateRequestDTO updateRequest) throws ClaimNotPresentException, PolicyNotExistsException;

}
