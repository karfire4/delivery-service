package com.org.startup.controller;


import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.startup.beans.jwt.AuthenticationBean;
import com.org.startup.beans.jwt.MinimalJWTUser;
import com.org.startup.dto.UserDto;
import com.org.startup.dto.exception.ExceptionResponse;
import com.org.startup.entity.User;
import com.org.startup.exception.UserNameAlreadyRegister;
import com.org.startup.service.security.jwt.JWTService;
import com.org.startup.service.user.UserService;
import com.sun.media.sound.InvalidDataException;


@RestController
public class JWTAuthController {

    @Autowired
    private JWTService jwtService;
	
	@Autowired
	private UserService userService;
	
    @PostMapping(value = "/api/public/auth")
    public ResponseEntity<?> auth(@RequestBody AuthenticationBean auth) {
    	
        String username = auth.getUsername();
        String password = auth.getPassword();
        
        Optional<User> user = this.userService.findUserByUsernameAndPassword(username, password);        		
        
        if (user.isPresent()) {
        	
            MinimalJWTUser jwtUser = new MinimalJWTUser(user.get().getUsername(), 
            		user.get()
                	.getUsersRoleses()
                	.stream()
                	.map(u -> u.getRole())
                	.collect(Collectors.toSet()));
            
            return ResponseEntity.ok(jwtService.getToken(jwtUser));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @PostMapping(value = "/api/public/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws InvalidDataException{
        
        User user = this.userService.registerNewUser(userDto);        		
        
        if (user != null) {
        	
            MinimalJWTUser jwtUser = new MinimalJWTUser(user.getUsername(), 
            		user.getUsersRoleses()
                	.stream()
                	.map(u -> u.getRole())
                	.collect(Collectors.toSet()));
            
            return ResponseEntity.ok(jwtService.getToken(jwtUser));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @ExceptionHandler(UserNameAlreadyRegister.class)
	public ResponseEntity<ExceptionResponse> handler(UserNameAlreadyRegister ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.ALREADY_REPORTED.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.ALREADY_REPORTED);
		return response;
	}
}
