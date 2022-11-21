package com.levi9.socialnetwork.Service.impl;

import java.util.List;

import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Repository.GroupRepository;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.dto.GroupDTO;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;
	
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}
	
	public Group getGroupById(Long id) throws ResourceNotFoundException {
		return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group is not found for this id ::" + id));
	}

	public Group createGroup(GroupDTO groupDTO) {
		Group group = new Group(groupDTO.isPrivate(), groupDTO.getIdAdmin());
		return groupRepository.save(group);
	}
	
	public Group updateGroup(Long groupId, @RequestBody GroupDTO groupDTO) throws ResourceNotFoundException {
		Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group is not found for this id ::" + groupId));
		group.setPrivate(groupDTO.isPrivate());
		groupRepository.save(group);
		
		return group;
	}
	
	public Group deleteGroup(Long groupId) throws ResourceNotFoundException{
		Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group is not found for this id ::" + groupId));
		groupRepository.delete(group);
		
		return group;
	}

	@Override
	public boolean acceptMember(Long userId, Long groupId) throws ResourceNotFoundException {

		Group group = getGroupById(groupId);
		boolean removed = group.getUserRequests().removeIf(user -> user.getId().equals(userId));
		if(!removed){
			throw new ResourceNotFoundException("User with id " + userId + " did not request joining this group!");
		}
		User user = userRepository.findById(userId).map(u -> u).orElseThrow();
		group.getMembers().add(user);
		groupRepository.save(group);

		return true;
	}
	
}
