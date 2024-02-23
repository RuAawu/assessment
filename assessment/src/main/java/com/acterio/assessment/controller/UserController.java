package com.acterio.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acterio.assessment.entity.User;
import com.acterio.assessment.service.UserService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/domain/count")
    public Map<String, Long> domainCount(){
    	return userService.getDomainCount();
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
    	return userService.registerUser(user);
      
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password){
    	 boolean success = userService.login(email, password);
         if (success) {
             return ResponseEntity.ok("Login successful");
         } else {
             return ResponseEntity.badRequest().body("Wrong email or password");
         }
     }
   
}
