package com.org.startup.service.user;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.startup.dto.UserDto;
import com.org.startup.entity.User;
import com.org.startup.entity.UsersRoles;
import com.org.startup.entity.enums.InvitationStatus;
import com.org.startup.exception.UserNameAlreadyRegister;
import com.org.startup.repository.UsersRepository;
import com.org.startup.repository.UsersRolesRepository;
import com.org.startup.service.security.password.PasswordService;
import com.sun.media.sound.InvalidDataException;


public interface UserService {

	public User findUserByUserName(String username) ;

	public boolean authenticate(String username, String password);

	public Optional<User> findUserByUsernameAndPassword(String username, String password);
	
	public User registerNewUser(UserDto userDto) throws InvalidDataException;
}
