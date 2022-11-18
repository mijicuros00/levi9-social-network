package com.levi9.socialnetwork.Service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceDuplicateException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.GroupRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.UserService;
import com.levi9.socialnetwork.dto.RequestDTO;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private GroupService groupService;
	
	public java.util.List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
		
	public ResponseEntity<User> getUserById(Long userId)
		throws ResourceNotFoundException{
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
	 return ResponseEntity.ok().body(user);
	}
	

	public User createUser(User user) {
		return userRepository.save(user);
	}
	

	public ResponseEntity<User> updateUser(Long userId,
			 @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		
		user.setName(userDetails.getName());
		user.setSurname(userDetails.getSurname());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
	
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	public Map<String, Boolean> deleteUser(Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@Override
	public boolean acceptMember(Long userId, Long groupId) throws ResourceNotFoundException {

		Group group = groupService.getGroupById(groupId);
		group.getUserRequests().removeIf(user -> user.getId().equals(userId));
		User user = userRepository.findById(userId).map(u -> u).orElseThrow();
		group.getMembers().add(user);
		groupRepository.save(group);

		return true;
	}

	@Transactional
	public User createGroupRequest(RequestDTO requestDTO) throws ResourceNotFoundException, ResourceDuplicateException {
		
		Group group = groupService.getGroupById(requestDTO.getIdGroup());
		User user = userRepository.findById(requestDTO.getIdUser()).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + requestDTO.getIdUser()));
		
		try {
			//user.getGroupRequests().add(group);	
			group.getUserRequests().add(user);
			userRepository.save(user);
			groupRepository.save(group);
		} catch (Exception ex) {
			throw new ResourceDuplicateException("Request already exists");
		}
		
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		return user;
	}
}

	
	
	
	
	
	

