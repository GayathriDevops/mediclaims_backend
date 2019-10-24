package com.hcl.helathcare.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcl.helathcare.dto.ClaimResponseDTO;
import com.hcl.helathcare.dto.UpdateRequestDTO;
import com.hcl.helathcare.dto.UpdateResponseDTO;
import com.hcl.helathcare.dto.ViewClaimsDTO;
import com.hcl.helathcare.exception.ClaimNotPresentException;
import com.hcl.helathcare.exception.PolicyNotExistsException;
import com.hcl.helathcare.service.ClaimService;
import com.hcl.helathcare.service.ClaimServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClaimControllerTest {

	@Mock
	ClaimServiceImpl claimServiceImpl;

	@Mock
	private ClaimService claimService;

	@InjectMocks
	private ClaimController claimController;


	private ClaimResponseDTO claimResponseDTO;
	private ViewClaimsDTO viewClaimsDTO;
	private UpdateRequestDTO updateRequestDTO;
	private UpdateResponseDTO updateResponseDTO;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		claimResponseDTO = new ClaimResponseDTO();
		claimResponseDTO.setUserId(1L);
		claimResponseDTO.setPolicyId(1L);
		claimResponseDTO.setHospitalName("hospitalName");
		claimResponseDTO.setDischargeSummary("dischargeSummary");
		claimResponseDTO.setDischargeDate(LocalDate.now());
		claimResponseDTO.setDiagnosis("diagnosis");
		claimResponseDTO.setAdmissionDate(LocalDate.now());
		claimResponseDTO.setClaimStatus("claimStatus");
		claimResponseDTO.setClaimId(1L);

		List<ClaimResponseDTO> claimsList = new ArrayList<>();
		claimsList.add(claimResponseDTO);

		updateRequestDTO = new UpdateRequestDTO();
		updateRequestDTO.setClaimId(1L);
		updateRequestDTO.setStatus("pending");

		updateResponseDTO = new UpdateResponseDTO();
		updateResponseDTO.setMessage("success");
		updateResponseDTO.setStatusCode(200);

	}


	/**
	 * @throws ClaimNotPresentException
	 * @throws PolicyNotExistsException
	 * @author sravya
	 */
	@Test
	public void updateClaimsTest() throws ClaimNotPresentException, PolicyNotExistsException {

		Mockito.when(claimServiceImpl.updateClaims(Mockito.any())).thenReturn(null);

		ResponseEntity<UpdateResponseDTO> updateClaims = claimController.updateClaims(updateRequestDTO);
		assertNotNull(updateClaims);
		assertEquals(updateResponseDTO.getStatusCode(), updateClaims.getBody().getStatusCode());

	}

	/**
	 * @throws ClaimNotPresentException
	 * @author sravya
	 */
	@Test
	public void viewClaimsTest() throws ClaimNotPresentException {

		List<ClaimResponseDTO> claimsList = new ArrayList<>();
		claimsList.add(claimResponseDTO);

		Mockito.when(claimServiceImpl.viewClaims(Mockito.anyString())).thenReturn(claimsList);

		viewClaimsDTO = new ViewClaimsDTO();
		viewClaimsDTO.setViewClaims(claimsList);
		ResponseEntity<ViewClaimsDTO> viewClaims = claimController.viewClaims(Mockito.anyString());
		assertNotNull(viewClaims);
	}
}
