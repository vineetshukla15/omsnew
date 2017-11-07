package com.api.email.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.api.email.exceptions.OMSSystemException;
import com.api.email.exceptions.Status;
import com.api.email.service.EmailService;
import com.api.email.settings.EmailUtill;

import freemarker.template.TemplateException;

@Component("emailService")
public class EmailServiceImpl implements EmailService{
	
	final static Logger LOGGER = Logger.getLogger(EmailServiceImpl.class);

/*	@Autowired
	private EmailRepository emailRepository;*/

@Value("${email.fromAdress}")
private String fromAdress;
@Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void send(String to, String subject, String body) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        helper = new MimeMessageHelper(message, true); // true indicates
        // multipart message
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(body, true);
        helper.setFrom(fromAdress);// true indicates html
        // continue using helper object for more functionalities like adding attachments, etc.

        javaMailSender.send(message);


    }
@Override
    public SimpleMailMessage constructEmail(String subject, String body, String emailid) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(emailid);
        email.setFrom("oms-support@Tavant.com");
        return email;
    }
    
@Override
    public final void sendEmailMessage(final String recipientAddress, final String templatefile, final Map<String,Object>templateKeyMatch, final String subject) {
        String body=null;
		try {
			body = EmailUtill.getTemplateAsString(templatefile, templateKeyMatch);
		} catch (IOException | TemplateException e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Unable to read template : "+templatefile);
		}
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(body);
        try {
			send(recipientAddress,subject,body);
		} catch (MessagingException e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Unable to send email for : "+subject);
		}
        
    }


}
