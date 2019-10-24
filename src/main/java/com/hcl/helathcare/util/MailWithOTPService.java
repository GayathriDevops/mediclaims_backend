package com.hcl.helathcare.util;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Component;

/**
 * 
 * Class to handle mail sending
 * 
 * 
 * 
 * @author Pradeep AJ
 *
 * 
 * 
 */

@Component

public class MailWithOTPService {

	private static final Logger logger = LoggerFactory.getLogger(MailWithOTPService.class);

	private JavaMailSender javaMailSender;

	/**
	 * 
	 * 
	 * 
	 * @param javaMailSender
	 * 
	 */

	@Autowired

	public MailWithOTPService(JavaMailSender javaMailSender) {

		this.javaMailSender = javaMailSender;

	}

	/**
	 * 
	 * This function is used to send mail without attachment.
	 * 
	 * 
	 * 
	 * @param user
	 * 
	 * @throws MailException
	 * 
	 */

	public void sendEmail(String email, String subject, String body) {

		logger.info("sending mail to {}=", email);

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(email);

		mail.setSubject(subject);

		mail.setText(body);

		javaMailSender.send(mail);

		logger.info("email sent successfully to {}=", email);

	}

}