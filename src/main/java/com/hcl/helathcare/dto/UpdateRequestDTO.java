package com.hcl.helathcare.dto;

public class UpdateRequestDTO {
	
	private String status;
	private long claimId;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getClaimId() {
		return claimId;
	}
	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}
	

}
