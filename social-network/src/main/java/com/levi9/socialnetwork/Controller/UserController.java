package com.levi9.socialnetwork.Controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.socialnetwork.Exception.ResourceDuplicateException;
import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Service.UserService;
import com.levi9.socialnetwork.dto.RequestDTO;



@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public java.util.List<User> getAllUsers() {
		return this.userService.getAllUsers();
	}

	@GetMapping("/{id}")
//	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN'")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		return userService.getUserById(userId);
	}
	
	
	@PostMapping("/{userId}/friend/{friendId}")
	public int addFriend(
			@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "friendId") Long friendId)
	{
		return userService.addFriend(userId,friendId);
	}
	

	

	@PostMapping()
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
			@RequestBody User userDetails) throws ResourceNotFoundException {
		return userService.updateUser(userId, userDetails);
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		return userService.deleteUser(userId);
	}
	
	@PostMapping("/create-request")
	public ResponseEntity<User> createGroupRequest(@RequestBody RequestDTO requestDTO) throws ResourceNotFoundException, ResourceDuplicateException {
		User user;
		try {
			user = userService.createGroupRequest(requestDTO);
		} catch (ResourceNotFoundException | ResourceDuplicateException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/accept-member/groups/{groupId}")
	public ResponseEntity<Boolean> acceptMember(@PathVariable Long groupId) throws ResourceNotFoundException {
		// User is hardcoded for now, it will be a user that sent the request when RBAC is implemented
		try{
			boolean success = userService.acceptMember(10L, groupId);
			return new ResponseEntity<>(success, HttpStatus.OK);
		}catch (ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
