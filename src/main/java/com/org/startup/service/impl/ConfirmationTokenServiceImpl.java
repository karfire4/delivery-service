package com.org.startup.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.startup.entity.ConfirmationToken;
import com.org.startup.entity.User;
import com.org.startup.repository.ConfirmationTokenRepository;
import com.org.startup.service.ConfirmationTokenService;
import com.org.startup.service.MailService;


@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private MailService mailService;

	@Override
	public void sendConfirmationMail(User user) {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        mailService.sendConfirmationEmail(user, confirmationToken.getConfirmationToken());

	}


	


}
