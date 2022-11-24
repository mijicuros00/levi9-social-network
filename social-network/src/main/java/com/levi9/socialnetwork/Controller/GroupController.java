package com.levi9.socialnetwork.Controller;

import java.security.Principal;
import java.util.List;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Model.Address;
import com.levi9.socialnetwork.Service.EventService;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.UserService;
import com.levi9.socialnetwork.Service.MuteGroupService;
import com.levi9.socialnetwork.Service.PostService;
import com.levi9.socialnetwork.Service.AddressService;
import com.levi9.socialnetwork.dto.AddressDTO;
import com.levi9.socialnetwork.dto.EventDTO;
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
import com.levi9.socialnetwork.dto.GroupDTO;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private MuteGroupService muteGroupService;
	
	@Autowired
	private PostService postService;

	@Autowired
	private EventService eventService;

	@Autowired
	private AddressService addressService;
	
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
	
    @GetMapping(value = "/{id-group}/posts")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable(value = "id-group") Long groupId) throws ResourceNotFoundException {
    
    	List<Post> visiblePosts = postService.getAllPostsFromGroup(groupId);
    	
		return ResponseEntity.ok().body(visiblePosts);
    	
    }
    

	@PostMapping("/{groupId}/accept-member/{userId}")
	public ResponseEntity<Boolean> acceptMember(@PathVariable Long groupId, @PathVariable Long userId, Principal principal) throws ResourceNotFoundException {

		User user = userService.findUserByUsername(principal.getName());
		Group group = groupService.getGroupById(user.getId());

		if(!group.getIdAdmin().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

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
	@Transactional
	public ResponseEntity<Void> removeMember(@PathVariable Long groupId, Principal principal) throws ResourceNotFoundException {

		User user = userService.findUserByUsername(principal.getName());
		Group group = groupService.getGroupById(user.getId());

		if(!group.getIdAdmin().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		muteGroupService.deleteMuteGroup(user.getId(), groupId);
		groupService.deleteMemberEvents(user.getId(), groupId);
		groupService.removeMember(user.getId(), groupId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/{groupId}/events")
	public ResponseEntity<EventDTO> createEventInGroup(@PathVariable Long groupId, @RequestBody EventDTO eventDTO, Principal principal)
			throws ResourceNotFoundException, ResourceExistsException {
		User user = userService.findUserByUsername(principal.getName());
		Group group = groupService.getGroupById(user.getId());

		if(!group.getIdAdmin().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		eventDTO.setUserId(user.getId());

		AddressDTO addressDTO = eventDTO.getLocation();
		Address address = addressService.createAddress(new Address(addressDTO));


		Event event = eventService.createEventInGroup(new Event(eventDTO), address, group);
		return new ResponseEntity<>(new EventDTO(event, address), HttpStatus.OK);
	}

}
