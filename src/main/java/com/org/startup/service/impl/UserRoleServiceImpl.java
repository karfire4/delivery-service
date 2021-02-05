package com.org.startup.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.startup.entity.User;
import com.org.startup.entity.UsersRoles;
import com.org.startup.repository.UsersRolesRepository;
import com.org.startup.service.user.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	 @Autowired
	    private UsersRolesRepository usersRolesRepository;

	@Override
	public void setUserRole(User newUser) {
		  UsersRoles newUserRole = new UsersRoles(newUser,"ROLE.USER");
		  Set<UsersRoles> setRoles = new HashSet();
		  setRoles.add(newUserRole);
		  newUser.setUsersRoleses(setRoles);
		  usersRolesRepository.save(newUserRole);
	}
	
}