package com.org.startup.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.org.startup.entity.User;
import com.org.startup.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private JavaMailSender javaMailSender;

	/**
	 * 
	 * @param javaMailSender
	 */
	@Autowired
	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	public void sendEmail(User user) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that dude...");
		javaMailSender.send(mail);
	}
	
	public void sendConfirmationEmail(User user,String confirmationToken) throws MailException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8080/delivery-service/api/public/confirm-account?token="+confirmationToken);
		javaMailSender.send(mailMessage);
	}

	/**
	 * This fucntion is used to send mail that contains a attachment.
	 * 
	 * @param user
	 * @throws MailException
	 * @throws MessagingException
	 */
	public void sendEmailWithAttachment(User user) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(user.getEmail());
		helper.setSubject("Testing Mail API with Attachment");
		helper.setText("Please find the attached document below.");
		ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
		helper.addAttachment(classPathResource.getFilename(), classPathResource);
		javaMailSender.send(mimeMessage);
	}

}