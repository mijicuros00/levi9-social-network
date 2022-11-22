package com.levi9.socialnetwork.Service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Controller.UserController;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.GroupRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.EmailService;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.UserService;
import com.levi9.socialnetwork.dto.RequestDTO;


@Service
public class UserServiceImpl implements UserService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private EmailService emailService;

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
	

	@Override
	public boolean removeFriend(Long userId, Long friendId) throws ResourceNotFoundException, ResourceExistsException {
	
		User user = userRepository.findById(userId).map(u -> u).orElseThrow();
		boolean removed = user.getFriends().removeIf(f -> f.getId().equals(friendId));
		
		if(!removed){
			throw new ResourceNotFoundException("Friend with id " + friendId + " does not exist !");
		}
		
		userRepository.save(user);
		return removed;
	}
	
	
	public List<User> getNotMutedUsers(Long groupId)
	{
		return this.userRepository.getNotMutedUsers(groupId);
	}
	
	

	public int addFriend( Long userId, Long friendId )
	{
		Optional<User> user1=  userRepository.findById(userId);
		User user= user1.get();
		
		Optional<User> user2=  userRepository.findById(friendId);
		User friend= user2.get();
		
		user.getFriends().add(friend);
		userRepository.save(user);

		return 5;
		
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


	@Transactional
	public User createGroupRequest(RequestDTO requestDTO) throws ResourceNotFoundException, ResourceExistsException {
		
		Group group = groupService.getGroupById(requestDTO.getIdGroup());
		User user = userRepository.findById(requestDTO.getIdUser()).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + requestDTO.getIdUser()));
		
		if(group.getUserRequests().contains(user)) {
			throw new ResourceExistsException("Resource already exists.");
		}
		group.getUserRequests().add(user);
		groupRepository.save(group);
		
		
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

	
	
	
	
	
	

