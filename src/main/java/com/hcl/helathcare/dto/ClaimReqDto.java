package com.hcl.helathcare.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClaimReqDto {
	
	private Long userId;
	private Long policyId;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private Double claimAmount;
	private String hospitalName;
	private String dischargeSummary;
	private String diagnosis;
	
}
