package com.levi9.socialnetwork.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.UserRepository;
import antlr.collections.List;

@Service
public class UserService {


	@Autowired
	private UserRepository UserRepository;
	
	public java.util.List<User> getAllUsers(){
		return this.UserRepository.findAll();
	}
		
	public ResponseEntity<User> getUserById(Long userId)
		throws ResourceNotFoundException{
		User user = UserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
	 return ResponseEntity.ok().body(user);
	}
	

	public User createUser(User user) {
		return UserRepository.save(user);
	}
	

	public ResponseEntity<User> updateUser(Long userId,
			 @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = UserRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		
		user.setName(userDetails.getName());
		user.setSurname(userDetails.getSurname());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		
	
		final User updatedUser = UserRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	public Map<String, Boolean> deleteUser(Long userId)
			throws ResourceNotFoundException {
		User user = UserRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

		UserRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

	
	
	
	
	
	

