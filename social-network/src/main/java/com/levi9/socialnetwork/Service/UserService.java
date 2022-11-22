package com.levi9.socialnetwork.Service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.levi9.socialnetwork.Exception.ResourceDuplicateException;
import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.dto.RequestDTO;

public interface UserService  extends UserDetailsService {

	public java.util.List<User> getAllUsers();

	public ResponseEntity<User> getUserById(Long userId)throws ResourceNotFoundException;

	public User createUser(User user);

	public int addFriend(Long userId, Long friendId );
	
	public boolean removeFriend(Long userId, Long friendId) throws ResourceNotFoundException, ResourceExistsException;
	
	public ResponseEntity<User> updateUser(Long userId, @RequestBody User userDetails) throws ResourceNotFoundException;
	
	public User createGroupRequest(RequestDTO requestDTO) throws ResourceNotFoundException, ResourceDuplicateException;

	public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException;


}
