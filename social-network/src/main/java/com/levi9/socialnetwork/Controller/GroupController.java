package com.levi9.socialnetwork.Controller;

import java.util.List;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Service.MuteGroupService;
import com.levi9.socialnetwork.Service.UserService;
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
import org.springframework.web.bind.annotation.RestController;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.dto.GroupDTO;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private MuteGroupService muteGroupService;
	
	@GetMapping
	public ResponseEntity<List<Group>> getAllGroups() {
		List<Group> groups = groupService.getAllGroups();
		if(groups != null) {
			return ResponseEntity.ok().body(groups);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Group> getGroup(@PathVariable(value = "id") Long groupId) throws ResourceNotFoundException{
		Group group = groupService.getGroupById(groupId);
		if(group != null) {
			return ResponseEntity.ok().body(group);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Group> createGroup(@RequestBody GroupDTO groupDTO) {
		groupService.createGroup(groupDTO);
		return ResponseEntity.status(200).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Group> updateGroup(@PathVariable(value = "id") Long groupId, @RequestBody GroupDTO groupDTO) throws ResourceNotFoundException {
		Group updatedGroup = groupService.updateGroup(groupId, groupDTO);
		if(updatedGroup != null) {
			return ResponseEntity.ok().body(updatedGroup);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Group> deleteGroup(@PathVariable(value="id") Long id) {
		Group group;
		try {
			group = groupService.deleteGroup(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(group);
	}

	@PostMapping("/{groupId}/accept-member/{userId}")
	public ResponseEntity<Boolean> acceptMember(@PathVariable Long groupId, @PathVariable Long userId) throws ResourceNotFoundException {
		//TODO: Check if user that sent request is really group admin
		try{
			boolean success = groupService.acceptMember(userId, groupId);
			return new ResponseEntity<>(success, HttpStatus.OK);
		}catch (ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ResourceExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{groupId}/remove-member/{userId}")
	public ResponseEntity<Void> removeMember(@PathVariable Long groupId, @PathVariable Long userId) throws ResourceNotFoundException {
		//TODO: Check if user that sent request is really group admin
		//TODO: Remove data from member_event table when implemented
		Group group = groupService.getGroupById(groupId);
		muteGroupService.deleteMuteGroup(userId, groupId);
		groupService.removeMember(userId, groupId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{groupId}/leave")
	public ResponseEntity<Void> leaveGroup(@PathVariable Long groupId) throws ResourceNotFoundException {
		//TODO: Get id of logged in user
		Long userId = 1L;
		muteGroupService.deleteMuteGroup(userId, groupId);
		groupService.removeMember(userId, groupId);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
