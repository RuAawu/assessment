package com.acterio.assessment.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import com.acterio.assessment.dao.UserRepository;
import com.acterio.assessment.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
   
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

	
    @Autowired
    private PasswordEncoder passwordEncoder;
    BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder();

    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email address already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
	

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }
	
	
	public Map<String, Long> getDomainCount() {
        List<String> domains = userRepository.getDomainNames();
        Map<String, Long> domainCount = new HashMap<>();
        for(String domain: domains) {
            Long count = domainCount.getOrDefault(domain, 0L);
            domainCount.put(domain, count + 1);
        }
        return domainCount;
    }
}

