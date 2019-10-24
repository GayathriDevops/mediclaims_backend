package com.hcl.helathcare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.entity.UserPolicy;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.PolicyNotExistsException;
import com.hcl.helathcare.repository.ClaimRepository;
import com.hcl.helathcare.repository.PolicyRepository;
import com.hcl.helathcare.repository.UserPolicyRepository;
import com.hcl.helathcare.repository.UserRepository;
import com.hcl.helathcare.util.Constants;

/**
 * ClaimServiceImpl implements methods for creating, viewing and updating claim
 * override all abstracts methods from ClaimService createNewClaim- it will
 * validate user,policy, oustatnding policy amount if is valid it will create
 * else thrwing exception
 * 
 * 
 */

@Service
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	ClaimRepository claimRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	UserPolicyRepository userPolicyRepository;

	private static final Logger logger = LoggerFactory.getLogger(ClaimServiceImpl.class);

	/**
	 * method view claims by admin returns list of all claims
	 * 
	 * @param roleName
	 * @returns List of pending-claims for approver
	 * @returns List of leve1-approved-claims for seniorApprover
	 * @exception ClaimsNotExistException
	 * @author sravya
	 */
	@Override
	public List<ClaimResponseDTO> viewClaims(String roleName) throws ClaimNotPresentException {

		logger.info("::Entered into------------: viewClaims()");

		String searchVariable = "";
		if (roleName.equalsIgnoreCase("approver")) {
			searchVariable = "pending";
		} else if (roleName.equalsIgnoreCase("seniorApprover")) {
			searchVariable = "approved1";
		}

		Optional<List<Claim>> claims = claimRepository.findAllByClaimStatus(searchVariable);

		if (!claims.isPresent())
			throw new ClaimNotPresentException(Constants.CLAIM_NOT_PRESENT);

		List<Claim> claimList = claims.get();

		List<ClaimResponseDTO> claimResult = new ArrayList<>();

		claimList.forEach(claim -> {
			ClaimResponseDTO result = new ClaimResponseDTO();
			BeanUtils.copyProperties(claim, result);
			claimResult.add(result);
		});

		return claimResult;
	}
	
	/**
	 * @param updateRequest
	 * @return UpdateRequestDTO
	 * @throws ClaimNotPresentException
	 * @throws PolicyNotExistsException
	 * @author sravya
	 */
	@Override
	public Claim updateClaims(UpdateRequestDTO updateRequest)
			throws ClaimNotPresentException, PolicyNotExistsException {

		logger.info("::Entered into------------: updateClaims()");

		Optional<Claim> optionalClaim = claimRepository.findByClaimId(updateRequest.getClaimId());
		if (!optionalClaim.isPresent())
			throw new ClaimNotPresentException(Constants.CLAIM_NOT_PRESENT);

		Claim claim = optionalClaim.get();
		claim.setClaimStatus(updateRequest.getStatus());
		Claim claimResult = claimRepository.save(claim);

		Optional<UserPolicy> userPolicy = userPolicyRepository.findByPolicyIdAndUserId(claimResult.getPolicyId(),
				claimResult.getUserId());
		if (!userPolicy.isPresent()) {
			throw new PolicyNotExistsException(Constants.POLICY_NOT_FOUND);
		}
		UserPolicy policy = userPolicy.get();
		if (claimResult.getClaimStatus().contentEquals(Constants.APPROVED)) {
			policy.setClaimOutstatnindBalance(policy.getClaimOutstatnindBalance() - claimResult.getClaimAmount());
			userPolicyRepository.save(policy);
		}
		return claimResult;
	}




}
