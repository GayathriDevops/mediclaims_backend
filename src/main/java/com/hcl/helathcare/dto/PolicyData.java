package com.hcl.helathcare.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PolicyData {
	
	private Long policyId;
	private String policyName;
	private double policyAmount;
	private LocalDate policyStartDate;
	private LocalDate policyEndDate;

}
