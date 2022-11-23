package com.levi9.socialnetwork.Service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.MuteGroup;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.GroupRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.MuteGroupService;
import com.levi9.socialnetwork.dto.GroupDTO;
import com.levi9.socialnetwork.dto.RequestDTO;

@Service
public class GroupServiceImpl implements GroupService {
	private static final String RESOURCE_NOT_FOUND_MESSAGE = "Group is not found for this id ::";
	
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MuteGroupService muteGroupService;

	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}
	
	public Group getGroupById(Long id) throws ResourceNotFoundException {
		return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + id));
	}

	public Group createGroup(GroupDTO groupDTO) {
		Group group = new Group(groupDTO.getName(), groupDTO.isPrivate(), groupDTO.getIdAdmin());
		return groupRepository.save(group);
	}
	
	public Group updateGroup(Long groupId, @RequestBody GroupDTO groupDTO) throws ResourceNotFoundException {
		Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + groupId));
		group.setPrivate(groupDTO.isPrivate());
		groupRepository.save(group);
		
		return group;
	}
	
	public Group deleteGroup(Long groupId) throws ResourceNotFoundException{
		Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + groupId));
		groupRepository.delete(group);
		
		return group;
	}
	
	public User addUserToGroup(RequestDTO requestDTO) throws ResourceNotFoundException, ResourceExistsException {
		Group group = groupRepository.findById(requestDTO.getIdGroup()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + requestDTO.getIdGroup()));
		User user = userRepository.findById(requestDTO.getIdUser()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + requestDTO.getIdGroup()));
	
		if(group.getUserRequests().contains(user)) {
			throw new ResourceExistsException("Request for group already exists.");
		}
		group.getMembers().add(user);
		groupRepository.save(group);
		
		return user;
		
	}


	@Override
	public boolean acceptMember(Long userId, Long groupId) throws ResourceNotFoundException, ResourceExistsException {

		Group group = getGroupById(groupId);
		boolean removed = group.getUserRequests().removeIf(user -> user.getId().equals(userId));
		if(!removed){
			throw new ResourceNotFoundException("User with id " + userId + " did not request joining this group!");
		}
		User user = userRepository.findById(userId).map(u -> u).orElseThrow();
		group.getMembers().add(user);
		groupRepository.save(group);

		MuteGroup muteGroup = new MuteGroup(userId, groupId, false, LocalDateTime.now());
		muteGroupService.createMuteGroup(muteGroup);

		return true;
	}

	@Override
	public boolean removeMember(Long userId, Long groupId) throws ResourceNotFoundException {
		Group group = getGroupById(groupId);
		boolean removed = group.getMembers().removeIf(user -> user.getId().equals(userId));
		if(!removed){
			throw new ResourceNotFoundException("User with id " + userId + " did not request joining this group!");
		}
		groupRepository.save(group);
		return true;
	}

}
