package com.org.startup.exception;


public class UserNameAlreadyRegister extends RuntimeException{

	public UserNameAlreadyRegister(String message) {
		super(message);
	}
}
