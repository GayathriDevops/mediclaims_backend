package com.hcl.helathcare.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ClaimResponseDTO {
	
		private Long claimId;
		private Long userId;
		private Long policyId;
		private LocalDate admissionDate;
		private LocalDate dischargeDate;
		private Double claimAmount;
		private String hospitalName;
		private String dischargeSummary;
		private String diagnosis;
		private String claimStatus;

}
