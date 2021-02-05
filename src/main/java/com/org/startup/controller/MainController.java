package com.org.startup.controller;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.startup.beans.jwt.MinimalJWTUser;
import com.org.startup.entity.ConfirmationToken;
import com.org.startup.entity.User;
import com.org.startup.entity.enums.InvitationStatus;
import com.org.startup.repository.ConfirmationTokenRepository;
import com.org.startup.repository.UsersRepository;
import com.org.startup.service.MailService;


@RestController
public class MainController {
	@Autowired(required=false)
	private MinimalJWTUser userContext;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
    @GetMapping(value = "/api/secure/request/{value}")
    public ResponseEntity<?> secureRequest(@PathVariable String value, Principal principal) {
    	
        String result = String.format("'%s' came from a secure channel.", userContext);
        User user = new User();
        user.setEmail("karfire10@gmail.com");
        this.mailService.sendEmail(user);
        
        return ResponseEntity.ok(result);
    }
 
    @GetMapping(value = "/api/public/request/{value}")
    public ResponseEntity<?> insecureRequest(@PathVariable String value) {
    	
        String result = String.format("'%s' came from a INsecure channel.", value);
        return ResponseEntity.ok(result);
    }
    
    @RequestMapping(value="/api/public/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        Map<String,String> errResponseMap = new HashMap<>();
        if(token != null)
        {
            User user = usersRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setStatus(InvitationStatus.VERIFIED);
            usersRepository.save(user);
            return ResponseEntity.ok("successfully verified");
        }
        else
        {
            errResponseMap.put("message", "The link is invalid or broken!");
            return ResponseEntity.badRequest().body(errResponseMap);
        }
    }
}
