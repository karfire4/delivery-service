package com.org.startup.service.user;


import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.org.startup.entity.User;

public interface UserRoleService {

	void setUserRole(User newUser);


	
}