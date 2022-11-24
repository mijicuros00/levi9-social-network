package com.levi9.socialnetwork.Controller;

import java.security.Principal;
import java.util.List;

import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Model.Address;
import com.levi9.socialnetwork.Model.MuteGroup;
import com.levi9.socialnetwork.Model.MuteDuration;
import com.levi9.socialnetwork.Service.EventService;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.UserService;
import com.levi9.socialnetwork.Service.MuteGroupService;
import com.levi9.socialnetwork.Service.PostService;
import com.levi9.socialnetwork.Service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;

import com.levi9.socialnetwork.dto.AddressDTO;
import com.levi9.socialnetwork.dto.EventDTO;
import com.levi9.socialnetwork.dto.GroupDTO;
import com.levi9.socialnetwork.dto.MuteGroupDTO;
import com.levi9.socialnetwork.dto.GroupResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/group")
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
    public ResponseEntity<List<GroupResponseDTO>> getAllGroups() {

        List<GroupResponseDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok().body(groups);
    }
  
	  @GetMapping("/{id}")
	  public ResponseEntity<GroupResponseDTO> getGroup(@PathVariable(value = "id") Long groupId) throws ResourceNotFoundException {
		    Group group = groupService.getGroupById(groupId);
		    GroupResponseDTO groupResponseDTO = new GroupResponseDTO(group);
		    return ResponseEntity.ok().body(groupResponseDTO);
	  }
	
	  @PostMapping
	  public ResponseEntity<Group> createGroup(@RequestBody GroupDTO groupDTO, Principal principal) {
	      if(principal == null) {
	          return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	      }
		    groupService.createGroup(groupDTO, principal);
		    return ResponseEntity.status(200).build();
	  }
	
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable(value = "id") Long groupId, @RequestBody GroupDTO groupDTO)
            throws ResourceNotFoundException {

        Group updatedGroup = groupService.updateGroup(groupId, groupDTO);
        return ResponseEntity.ok().body(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        Group group;
        group = groupService.deleteGroup(id);
        return ResponseEntity.ok().body(group);
    }

    @GetMapping(value = "/{id-group}/posts")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable(value = "id-group") Long groupId)
            throws ResourceNotFoundException {

        List<Post> visiblePosts = postService.getAllPostsFromGroup(groupId);
        return ResponseEntity.ok().body(visiblePosts);
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long groupId, Principal principal) throws ResourceNotFoundException {
        User user = userService.findUserByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = user.getId();
        muteGroupService.deleteMuteGroup(userId, groupId);
        groupService.removeMember(userId, groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{groupId}/events")
    public ResponseEntity<EventDTO> createEventInGroup(@PathVariable Long groupId, @RequestBody EventDTO eventDTO)
            throws ResourceNotFoundException, ResourceExistsException {

        // TODO: Use logged in user id
        Long userId = 1L;
        eventDTO.setUserId(userId);
        AddressDTO addressDTO = eventDTO.getLocation();
        Address address = addressService.createAddress(new Address(addressDTO));
        Group group = groupService.getGroupById(groupId);
        Event event = eventService.createEventInGroup(new Event(eventDTO), address, group);

        return new ResponseEntity<>(new EventDTO(event, address), HttpStatus.OK);
    }

    @PutMapping("/{groupId}/mute")
    public ResponseEntity<MuteGroupDTO> muteGroupForDuration(@PathVariable(value = "groupId") Long groupId, @RequestBody String muteDurationName, Principal principal)
            throws ResourceNotFoundException, ResourceExistsException {
        User user = userService.findUserByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        MuteDuration muteDuration = muteGroupService.getMuteDurationFromString(muteDurationName);
        MuteGroup muteGroup = muteGroupService.muteGroup(user.getId(), groupId, muteDuration);
        return new ResponseEntity<>(new MuteGroupDTO(muteGroup), HttpStatus.OK);
    }

    @PutMapping("/{groupId}/unmute")
    public ResponseEntity<MuteGroupDTO> unmuteGroup(@PathVariable(value = "groupId") Long groupId, Principal principal) throws ResourceNotFoundException {
        User user = userService.findUserByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        MuteGroup muteGroup = muteGroupService.unmuteGroup(user.getId(), groupId);
        return new ResponseEntity<>(new MuteGroupDTO(muteGroup), HttpStatus.OK);
    }
    

	@PostMapping("/{groupId}/accept-member/{userId}")
	public ResponseEntity<Boolean> acceptMember(@PathVariable Long groupId, @PathVariable Long userId, Principal principal) throws ResourceNotFoundException, ResourceExistsException {

		User user = userService.findUserByUsername(principal.getName());
		Group group = groupService.getGroupById(user.getId());

		if(!group.getIdAdmin().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

			boolean success = groupService.acceptMember(userId, groupId);
			return new ResponseEntity<>(success, HttpStatus.OK);
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
