package com.org.startup.service;

import com.org.startup.entity.User;

public interface ConfirmationTokenService {
	void sendConfirmationMail(User user);
	
}
