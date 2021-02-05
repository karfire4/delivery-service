package com.org.startup.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.startup.dto.UserDto;
import com.org.startup.entity.User;
import com.org.startup.entity.enums.InvitationStatus;
import com.org.startup.exception.UserNameAlreadyRegister;
import com.org.startup.repository.UsersRepository;
import com.org.startup.service.ConfirmationTokenService;
import com.org.startup.service.MailService;
import com.org.startup.service.security.password.PasswordService;
import com.org.startup.service.user.UserRoleService;
import com.org.startup.service.user.UserService;
import com.sun.media.sound.InvalidDataException;

 
@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UsersRepository usersRepository;
     
    @Autowired
    private PasswordService passwordService;
    
    @Autowired
    private UserRoleService userRoleService;  
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    public User findUserByUserName(String username) {
        return this.usersRepository.findByUsername(username);
    }
 
    public boolean authenticate(String username, String password) {
    	
        User user = findUserByUserName(username);
        
        return this.passwordService.equals(password, user.getPassword());
    }
    
    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
    	
    	User user = this.findUserByUserName(username);
    	
    	return this.passwordService.equals(password, user.getPassword()) ? Optional.of(user) : Optional.empty();    	
    }
    
  public User registerNewUser(UserDto userDto) throws InvalidDataException {
	  if(userDto.getUsername().equals("") ||  userDto.getPassword().equals("")||userDto.getMobile().equals("") || userDto.getEmail().equals("")) {
		  throw new InvalidDataException("entities fields are not empty required");
	  }
	  User user = findUserByUserName(userDto.getUsername());
	  if(user != null) {
		  throw new UserNameAlreadyRegister("username is already register,try different one");
	  }
	  String encodePassword = this.passwordService.encode(userDto.getPassword());
	  User newUser = new User(userDto.getUsername(), encodePassword, InvitationStatus.NOTVERIFIED);
	  newUser.setEmail(userDto.getEmail());
	  newUser.setMobile(userDto.getMobile());
	  User responseUser = this.usersRepository.save(newUser);
	  this.userRoleService.setUserRole(newUser);
	  this.confirmationTokenService.sendConfirmationMail(responseUser);
	  return responseUser;
    }
}
