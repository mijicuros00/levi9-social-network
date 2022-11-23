package com.levi9.socialnetwork.Controller;

import java.util.List;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Model.Address;
import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Service.*;
import com.levi9.socialnetwork.dto.AddressDTO;
import com.levi9.socialnetwork.dto.EventDTO;
import liquibase.pro.packaged.A;
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
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.dto.GroupDTO;

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
    public ResponseEntity<List<Group>> getAllGroups() throws ResourceNotFoundException {

        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok().body(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable(value = "id") Long groupId) throws ResourceNotFoundException {

        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok().body(group);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupDTO groupDTO) {

        groupService.createGroup(groupDTO);
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

    @PostMapping("/{groupId}/accept-member/{userId}")
    public ResponseEntity<Boolean> acceptMember(@PathVariable Long groupId, @PathVariable Long userId)
            throws ResourceNotFoundException, ResourceExistsException {

        // TODO: Check if user that sent request is really group admin
        boolean success = groupService.acceptMember(userId, groupId);
        return new ResponseEntity<>(success, HttpStatus.OK);

    }

    @DeleteMapping("/{groupId}/remove-member/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long groupId, @PathVariable Long userId)
            throws ResourceNotFoundException {

        // TODO: Check if user that sent request is really group admin
        // TODO: Remove data from member_event table when implemented
        Group group = groupService.getGroupById(groupId);
        muteGroupService.deleteMuteGroup(userId, groupId);
        groupService.removeMember(userId, groupId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

}
