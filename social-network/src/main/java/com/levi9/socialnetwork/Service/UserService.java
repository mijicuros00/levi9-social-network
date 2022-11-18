package com.levi9.socialnetwork.Service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.dto.RequestDTO;

public interface UserService  extends UserDetailsService {

	public java.util.List<User> getAllUsers();

	public ResponseEntity<User> getUserById(Long userId)throws ResourceNotFoundException;

	public User createUser(User user);

	public ResponseEntity<User> updateUser(Long userId, @RequestBody User userDetails) throws ResourceNotFoundException;
	
	public User createGroupRequest(RequestDTO requestDTO) throws ResourceNotFoundException, SQLException;

	public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException;
}
