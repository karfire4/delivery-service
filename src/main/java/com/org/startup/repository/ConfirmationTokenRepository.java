package com.org.startup.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.startup.entity.ConfirmationToken;
import com.org.startup.entity.UsersRoles;


@Repository
public interface ConfirmationTokenRepository  extends JpaRepository<ConfirmationToken, String>{
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
