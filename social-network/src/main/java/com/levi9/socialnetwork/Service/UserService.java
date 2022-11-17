package com.levi9.socialnetwork.Service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.User;

public interface UserService  extends UserDetailsService {

	public java.util.List<User> getAllUsers();

	public ResponseEntity<User> getUserById(Long userId)throws ResourceNotFoundException;

	public User createUser(User user);

	public ResponseEntity<User> updateUser(Long userId, @RequestBody User userDetails) throws ResourceNotFoundException;

	public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException;
}
