package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.*;
import com.levi9.socialnetwork.Service.*;
import com.levi9.socialnetwork.dto.AddressDTO;
import com.levi9.socialnetwork.dto.EventDTO;
import com.levi9.socialnetwork.dto.GroupDTO;
import com.levi9.socialnetwork.dto.MuteGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{groupId}/events")
    public ResponseEntity<List<EventDTO>> getEventsInGroup(@PathVariable Long groupId) throws ResourceNotFoundException {
        List<Event> groupEvents = eventService.getAllEventsInGroup(groupId);
        List<EventDTO> groupEventDTOs = new ArrayList<>();
        for (Event event : groupEvents) {
            Address address = addressService.getAddressById(event.getLocationId());
            EventDTO eventDTO = new EventDTO(event, address);
            groupEventDTOs.add(eventDTO);
        }
        return new ResponseEntity<>(groupEventDTOs, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/events")
    public ResponseEntity<EventDTO> createEventInGroup(@PathVariable Long groupId, @RequestBody EventDTO eventDTO, Principal principal)
            throws ResourceNotFoundException, ResourceExistsException {
        User user = userService.findUserByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = user.getId();

        Group group = groupService.getGroupById(groupId);
        if (!group.containsUser(userId)) {
            throw new ResourceNotFoundException("User with id " + userId + " is not a member of group with id " + groupId);
        }

        eventDTO.setUserId(userId);
        AddressDTO addressDTO = eventDTO.getLocation();
        Address address = addressService.createAddress(new Address(addressDTO));
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

}
