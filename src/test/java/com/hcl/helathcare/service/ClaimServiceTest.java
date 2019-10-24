package com.hcl.helathcare.service;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcl.helathcare.dto.ClaimDetails;
import com.hcl.helathcare.dto.ClaimResponse;
import com.hcl.helathcare.entity.Claim;
import com.hcl.helathcare.repository.ClaimRepository;
import com.hcl.helathcare.repository.PolicyRepository;
import com.hcl.helathcare.service.ClaimServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClaimServiceTest {

	@InjectMocks
	private ClaimServiceImpl claimServiceImpl;

	@Mock
	private ClaimRepository claimRepository;
	@Mock
	private PolicyRepository policyRepository;

	@Test
	public void getAllClaimServiceTestByUser() {
		ClaimDetails cl = new ClaimDetails();
		Claim c = new Claim();
		List<ClaimDetails> claims = new ArrayList<ClaimDetails>();
		cl.setAdmissionDate(LocalDate.now());
		cl.setClaimAmount(10000);
		cl.setClaimId(1L);
		cl.setClaimStatus("Approved");
		cl.setDiagnosis("Cardio");
		cl.setDischargeDate(LocalDate.now());
		cl.setDischargeSummary("ffff");
		cl.setHospitalName("Appollo");
		cl.setPolicyId(1L);
		cl.setUserId(1L);
		claims.add(cl);

		List<Claim> cList = new ArrayList<Claim>();
		c.setAdmissionDate(LocalDate.now());
		c.setClaimAmount(10000D);
		c.setClaimId(1L);
		c.setClaimStatus("Approved");
		c.setDiagnosis("Cardio");
		c.setDischargeDate(LocalDate.now());
		c.setDischargeSummary("ffff");
		c.setFileName(null);
		c.setHospitalName("Appollo");
		c.setPolicyId(1L);
		c.setUserId(1L);
		cList.add(c);
		ClaimResponse cla = new ClaimResponse();
		cla.setClientDetails(claims);
		Mockito.when(claimRepository.findByUserId(Mockito.any())).thenReturn(cList);
		assertEquals(cList, claimRepository.findByUserId(1L));

	}

	@Test
	public void getAllPolicesServiceTestByUser() {
		List<Object[]> objects = new ArrayList<Object[]>();
		Object[] obj = { 1, "cardio", 38766, "2016-04-01", "2012-05-01" };
		objects.add(obj);

		Mockito.when(policyRepository.getPolicesByUserId(Mockito.any())).thenReturn(objects);
		assertEquals(objects, policyRepository.getPolicesByUserId(1L));

	}

}
