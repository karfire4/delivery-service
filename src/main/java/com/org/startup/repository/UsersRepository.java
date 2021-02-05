package com.org.startup.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.startup.entity.User;


@Repository
public interface UsersRepository extends JpaRepository<User, Long>{

	public User findByUsernameAndPassword(String username, String password);
	public User findByUsername(String username);
	User findByEmailIgnoreCase(String emailId);
}
