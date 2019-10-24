package com.hcl.helathcare.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ClaimDetails {
	
	private Long claimId;
	private LocalDate admissionDate;
	private double claimAmount;
	private String claimStatus;
	private String diagnosis;
	private LocalDate dischargeDate;
	private String dischargeSummary;
	private String hospitalName;
	private Long policyId;
	private Long userId;
	
}
