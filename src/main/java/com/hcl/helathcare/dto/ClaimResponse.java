package com.hcl.helathcare.dto;


import java.util.List;

import lombok.Data;

@Data
public class ClaimResponse {

	List<ClaimDetails> clientDetails;
	
}
