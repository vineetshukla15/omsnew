package com.api.email.service;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

	public SimpleMailMessage constructEmail(String subject, String body, String emailid);

	public void send(String to, String subject, String body) throws MessagingException;

	public void sendEmailMessage(String recipientAddress, String templatefile,
			Map<String, Object> templateKeyMatch, String subject);

}
