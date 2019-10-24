package com.hcl.helathcare.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.entity.Policy;
import com.hcl.helathcare.entity.User;
import com.hcl.helathcare.entity.UserPolicy;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.PolicyNotExistsException;
import com.hcl.helathcare.repository.ClaimRepository;
import com.hcl.helathcare.repository.PolicyRepository;
import com.hcl.helathcare.repository.UserPolicyRepository;
import com.hcl.helathcare.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ClaimServiceImplTest {

	@Mock
	ClaimRepository claimRepository;

	@Mock
	private PolicyRepository policyRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserPolicyRepository userPolicyRepository;
	

	@InjectMocks
	private ClaimServiceImpl claimServiceImpl;

	
	private Claim claim;
	private User user;
	private Policy policy;
	private UserPolicy userPolicy;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		user = new User();
		user.setUserId(1L);
		user.setEmail("pradeep.aj28@gmail.com");
		user.setPassword("pradeep");
		policy = new Policy();
		policy.setPolicyId(1L);
		policy.setPolicyName("eye");
		policy.setPolicyAmount(200000.0);
		userPolicy = new UserPolicy();
		userPolicy.setUpId(1L);
		userPolicy.setClaimOutstatnindBalance(10000.0);
		userPolicy.setPolicyId(1L);
		userPolicy.setUserId(1L);

		Claim claim = new Claim();
		claim.setUserId(1L);
		claim.setClaimId(1L);
		claim.setAdmissionDate(LocalDate.now());
		claim.setClaimAmount(20000D);
		claim.setClaimStatus("aprroved");
		claim.setDiagnosis("jhfjvhks");
		claim.setDischargeDate(LocalDate.now());
		claim.setFileName("file");
		claim.setHospitalName("appolo");
		claim.setPolicyId(1L);
		claim.setClaimAmount(100D);

		UserPolicy userPolicy = new UserPolicy();
		userPolicy.setClaimOutstatnindBalance(1000D);
		userPolicy.setPolicyEndDate(LocalDate.now());
		userPolicy.setPolicyStartDate(LocalDate.now());
		userPolicy.setUpId(1L);
		userPolicy.setUserId(1L);
		userPolicy.setPolicyId(1L);

	}

	/**
	 * @throws Exception
	 * @author sravya
	 */
	@Test
	public void testViewClaims() throws Exception {

		String approverStatus = "pending";

		List<Claim> claims = new ArrayList<>();

		Optional<List<Claim>> claimList = Optional.of(claims);

		Mockito.when(claimRepository.findAllByClaimStatus(Mockito.anyString())).thenReturn(claimList);

		List<ClaimResponseDTO> viewClaims = claimServiceImpl.viewClaims(approverStatus);

		assertNotNull(viewClaims);
		assertEquals(claims.size(), viewClaims.size());
	}

	/**
	 * @throws ClaimNotPresentException
	 * @throws PolicyNotExistsException
	 * @author sravya
	 */
	@Test
	public void updateClaims() throws ClaimNotPresentException, PolicyNotExistsException {

		UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
		updateRequestDTO.setClaimId(1L);
		updateRequestDTO.setStatus("approved");

		Optional<Claim> optClaim = Optional.of(claim);

		Mockito.when(claimRepository.findByClaimId(Mockito.anyLong())).thenReturn(optClaim);

		Mockito.when(claimRepository.save(Mockito.any())).thenReturn(claim);

		Optional<UserPolicy> policy = Optional.of(userPolicy);
		Mockito.when(userPolicyRepository.findByPolicyIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(policy);

		Mockito.when(userPolicyRepository.save(userPolicy)).thenReturn(userPolicy);

		Claim updateClaims = claimServiceImpl.updateClaims(updateRequestDTO);

		assertNotNull(updateClaims);
		assertEquals(claim.getClaimId(), updateClaims.getClaimId());

	}

	/**
	 * @throws Exception
	 * @author sravya
	 */
	@Test(expected = ClaimNotPresentException.class)
	public void claimNotPresentExceptionTest1() throws Exception {
		String approverStatus = "approved111";

		Mockito.when(claimRepository.findAllByClaimStatus(approverStatus)).thenReturn(Optional.ofNullable(null));

		List<ClaimResponseDTO> viewClaims = claimServiceImpl.viewClaims(approverStatus);
		assertNotNull(viewClaims);
		assertEquals(0, viewClaims.size());
	}
	
	
	/**
	 * @throws Exception
	 * @author sravya
	 */
	@Test(expected = PolicyNotExistsException.class)
	public void policyNotExistsExceptionTest() throws Exception {
		
		UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
		updateRequestDTO.setClaimId(2L);
		updateRequestDTO.setStatus("approved");
		
		Optional<Claim> optClaim = Optional.of(claim);

		Mockito.when(claimRepository.findByClaimId(Mockito.anyLong())).thenReturn(optClaim);

		Mockito.when(claimRepository.save(Mockito.any())).thenReturn(claim);

		Mockito.when(userPolicyRepository.findByPolicyIdAndUserId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

		Claim claimResult = claimServiceImpl.updateClaims(updateRequestDTO);
		assertNotNull(claimResult);
		assertEquals("approved",claimResult.getClaimStatus());

	}

}
