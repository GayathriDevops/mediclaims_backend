package com.hcl.helathcare.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.helathcare.dto.ClaimDetails;
import com.hcl.helathcare.dto.ClaimReqDto;
import com.hcl.helathcare.dto.ClaimResponse;
import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.PolicyData;
import com.hcl.helathcare.dto.PolicyResponse;
import com.hcl.helathcare.dto.ResponseDto;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.entity.Policy;
import com.hcl.helathcare.entity.User;
import com.hcl.helathcare.entity.UserPolicy;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.InvalidClaimAmountException;
import com.hcl.helathcare.exception.NoPolicyNotFound;
import com.hcl.helathcare.exception.PolicyNotExistsException;
import com.hcl.helathcare.exception.UserNotExistsException;
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

	/**
	 * Method will validate user, policy, claim amount it is valid it will create
	 * new claim else throw exception
	 * 
	 * @param-ClaimReqDto
	 * @return-ResponseDto
	 * @exception-UserNotExistsException, InvalidClaimAmountException,
	 *                                    PolicyNotExistsException
	 */
	@Override
	public ResponseDto createNewClaim(ClaimReqDto request)
			throws UserNotExistsException, InvalidClaimAmountException, PolicyNotExistsException {

		logger.info("::Entered into------------: createNewClaim()");

		Optional<User> userExists = userRepository.findById(request.getUserId());
		if (userExists.isPresent()) {
			Optional<Policy> policyExists = policyRepository.findById(request.getPolicyId());
			if (policyExists.isPresent()) {

				Optional<UserPolicy> userPolicyEXistts = userPolicyRepository
						.findByPolicyIdAndUserId(request.getPolicyId(), request.getUserId());
				if (userPolicyEXistts.isPresent()
						&& request.getClaimAmount() <= userPolicyEXistts.get().getClaimOutstatnindBalance()) {
					Claim claim = new Claim();
					BeanUtils.copyProperties(request, claim);
					claim.setClaimStatus(Constants.CLAIM_STATUS);
					claimRepository.save(claim);

					return ResponseDto.builder().message(Constants.CLAIM_SUCCESS_MESSAGE).statusCode(Constants.CREATED)
							.build();

				} else {
					throw new InvalidClaimAmountException(Constants.INVALID_CLAIM_AMOUNT);
				}

			} else {
				throw new PolicyNotExistsException(Constants.POLICY_NOT_EXISTS);
			}
		} else {
			throw new UserNotExistsException(Constants.USER_NOT_EXISTS);
		}

	}

	/** 
	 * @param getClaimsByUser-Long userId
	 * @return ClaimResponse
	 * @author vishnu
	 */
	@Override
	public ClaimResponse getClaimsByUser(Long userId) {
		logger.info("Claim ::----------={}", userId);

		List<Claim> claimsList = claimRepository.findByUserId(userId);

		ClaimResponse response = new ClaimResponse();

		List<ClaimDetails> cliamList = new ArrayList<>();
		if (!claimsList.isEmpty()) {

			claimsList.stream().forEach(c -> {

				ClaimDetails details = new ClaimDetails();
				details.setAdmissionDate(c.getAdmissionDate());
				details.setClaimAmount(c.getClaimAmount());
				details.setClaimId(c.getClaimId());
				details.setClaimStatus(c.getClaimStatus());
				details.setDiagnosis(c.getDiagnosis());
				details.setDischargeDate(c.getDischargeDate());

				details.setHospitalName(c.getHospitalName());
				details.setPolicyId(c.getPolicyId());
				details.setUserId(c.getUserId());
				cliamList.add(details);
			});
			response.setClientDetails(cliamList);
		} else {
			throw new NoPolicyNotFound(Constants.CLAIMS_NOT_FOUND);
		}

		return response;
	}

	/** 
	 * @param getPolicesByUserId-Long userId
	 * @return PolicyResponse
	 * @author shankar
	 */
	@Override
	public PolicyResponse getPolicesByUserId(Long userId) {
		logger.info("Policy ::----------={}", userId);
		PolicyResponse response = new PolicyResponse();
		List<Object[]> objects = policyRepository.getPolicesByUserId(userId);
		if (!objects.isEmpty()) {
			List<PolicyData> polices = new ArrayList<>();
			objects.stream().forEach(obj -> {
				PolicyData pd = new PolicyData();
				pd.setPolicyId(Long.parseLong(obj[0].toString()));
				pd.setPolicyName(obj[1].toString());
				pd.setPolicyAmount(Double.parseDouble(obj[2].toString()));
				pd.setPolicyStartDate(LocalDate.parse(obj[3].toString()));
				pd.setPolicyEndDate(LocalDate.parse(obj[4].toString()));
				polices.add(pd);
			});
			response.setPolices(polices);
		} else {
			throw new NoPolicyNotFound(Constants.POLICY_NOT_FOUND);
		}

		return response;
	}
}
