package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.MuteGroupService;
import com.levi9.socialnetwork.Service.UserService;
import com.levi9.socialnetwork.dto.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    MuteGroupService muteGroupService;

    @GetMapping
    public java.util.List<User> getAllUsers() {

        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
//	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN'")
//	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {

        return userService.getUserById(userId);
    }

    @PostMapping("/{userId}/friend/{friendId}")
    public int addFriend(@PathVariable(value = "userId") Long userId, @PathVariable(value = "friendId") Long friendId) {

        return userService.addFriend(userId, friendId);
    }

    @PutMapping("/{userId}/remove-friend/{friendId}")
    public ResponseEntity<Boolean> removeFriend(@PathVariable Long userId, @PathVariable Long friendId)
            throws ResourceNotFoundException, ResourceExistsException {
        // TODO: userId - remove when you complete autenfication

        boolean success = userService.removeFriend(userId, friendId);
        return new ResponseEntity<>(success, HttpStatus.OK);

    }

    @PostMapping()
    public User createUser(@RequestBody User user) {

        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails)
            throws ResourceNotFoundException {

        return userService.updateUser(userId, userDetails);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {

        return userService.deleteUser(userId);
    }

    @PostMapping("/group-request")
    public ResponseEntity<User> createGroupRequest(@RequestBody RequestDTO requestDTO)
            throws ResourceNotFoundException, ResourceExistsException {

        User user;

        Group group = groupService.getGroupById(requestDTO.getIdGroup());
        if (group.isPrivate()) {
            user = userService.createGroupRequest(requestDTO);
        } else {
            user = groupService.addUserToGroup(requestDTO);
        }

        return ResponseEntity.ok().body(user);
    }
}
