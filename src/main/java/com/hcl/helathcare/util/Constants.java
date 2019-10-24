package com.hcl.helathcare.util;

/**
 * constants using throughout the application
 * @author Pradeep AJ
 *
 */
public class Constants {

	private Constants() {

	}

	public static final String INVAID_PATH = "Sorry! Filename contains invalid path sequence ";
	public static final String LOG_SUCCESS_MESSAGE = " Login Successfull";
	public static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated the status";
	public static final String USER_EXISTS = "There is already a user registered with the email provided";
	public static final String CLAIM_SUCCESS_MESSAGE = " Claim  submitted Successfully";
	public static final String INVALID_CREDENTIALS = "Please Enter Valid Credentails";
	public static final String USER_NOT_EXISTS = "User Not exsits";
	public static final String FILE_STORAGE_ERROR = "Could not store file  Please try again!";
	public static final String POLICY_NOT_EXISTS = "Requested Policy Not Available";
	public static final Integer CREATED = 201;

	public static final String CLAIM_NOT_PRESENT = "claims not present";
	public static final String APPROVED = "approved";
	public static final String POLICY_NOT_FOUND="policies not found";
	public static final String CLAIMS_NOT_FOUND="claims not found";

	public static final String CLAIM_STATUS="PENDING";
	public static final String INVALID_CLAIM_AMOUNT="Claim amount must be less than or equal to policy outstanding amount";
	public static final Integer OK = 200;
	public static final String CLAIM_DOC_PATH="./uploads";
	public static final String CLAIM_MAIL_SUBJECT = "ING Bank Transactions";
	public static final String CLAIM_MAIL_BODY = "Claim  submitted Successfully. Your Claim id is:";
	public static final String APPROVED2 = "approved2";
	

}
