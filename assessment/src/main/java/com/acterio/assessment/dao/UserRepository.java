package com.acterio.assessment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acterio.assessment.entity.User;



public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
	User findByEmail(String email);
	
	@Query("SELECT REGEXP_SUBSTR(email,'@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})') AS domain FROM UserTable")
	List<String> getDomainNames();
	

}


