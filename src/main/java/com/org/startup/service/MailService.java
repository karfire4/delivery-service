package com.org.startup.service;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.org.startup.entity.User;

public interface MailService {

	public void sendEmail(User user) throws MailException;

	public void sendEmailWithAttachment(User user) throws MailException, MessagingException;
	
	public void sendConfirmationEmail(User user,String confirmationToken) throws MailException;
}