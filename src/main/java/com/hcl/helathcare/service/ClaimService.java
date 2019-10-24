package com.hcl.helathcare.service;

import java.util.List;

import com.hcl.helathcare.dto.ClaimReqDto;
import com.hcl.helathcare.dto.ClaimResponse;
import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.PolicyResponse;
import com.hcl.helathcare.dto.ResponseDto;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.InvalidClaimAmountException;
import com.hcl.helathcare.exception.PolicyNotExistsException;
import com.hcl.helathcare.exception.UserNotExistsException;

public interface ClaimService {

	/**
	 * @param updateRequest
	 * @return
	 * @throws ClaimNotPresentException
	 * @throws PolicyNotExistsException
	 */
	
	List<ClaimResponseDTO> viewClaims(String roleName) throws ClaimNotPresentException;

	Claim updateClaims(UpdateRequestDTO updateRequest) throws ClaimNotPresentException, PolicyNotExistsException;
	
	ResponseDto createNewClaim(ClaimReqDto request)  throws  UserNotExistsException, InvalidClaimAmountException, PolicyNotExistsException;
	
	public ClaimResponse getClaimsByUser(Long userId);

	public PolicyResponse getPolicesByUserId(Long userId);

}
