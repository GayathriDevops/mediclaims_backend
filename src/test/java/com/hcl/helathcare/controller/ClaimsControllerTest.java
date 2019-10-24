package com.hcl.helathcare.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcl.helathcare.dto.ClaimDetails;
import com.hcl.helathcare.dto.ClaimResponse;
import com.hcl.helathcare.dto.PolicyData;
import com.hcl.helathcare.dto.PolicyResponse;
import com.hcl.helathcare.service.ClaimServiceImpl;

/**
 * @author shankar
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ClaimsControllerTest {

	@InjectMocks
	ClaimController claimController;

	@Mock
	ClaimServiceImpl claimServiceImpl;

	@Test
	public void getAllClaimControllerTest() {

		ClaimResponse response = new ClaimResponse();
		List<ClaimDetails> cliamList = new ArrayList<ClaimDetails>();
		ClaimDetails details = new ClaimDetails();
		details.setAdmissionDate(LocalDate.now());
		details.setClaimAmount(20000);
		details.setClaimId(1L);
		details.setClaimStatus("Pending");
		details.setDiagnosis("cardio");
		details.setDischargeDate(LocalDate.now());
		details.setDischargeSummary("ffff");
		details.setHospitalName("appllo");
		details.setPolicyId(1L);
		details.setUserId(1L);
		cliamList.add(details);
		response.setClientDetails(cliamList);
		Mockito.when(claimServiceImpl.getClaimsByUser(1L)).thenReturn(response);
		assertEquals(claimController.getClaimsByUser(1L).getBody(), response);
	}

	@Test
	public void getAllPolicyControllerTest() {
		PolicyResponse response = new PolicyResponse();
		List<PolicyData> polices = new ArrayList<PolicyData>();
		PolicyData pdata = new PolicyData();
		pdata.setPolicyAmount(30000);
		pdata.setPolicyEndDate(LocalDate.now());
		pdata.setPolicyId(1L);
		pdata.setPolicyName("cardio");
		pdata.setPolicyStartDate(LocalDate.now());
		polices.add(pdata);
		response.setPolices(polices);
		Mockito.when(claimServiceImpl.getPolicesByUserId(1L)).thenReturn(response);
		assertEquals(claimController.getPolicyByUser(1L).getBody(), response);
	}

}
