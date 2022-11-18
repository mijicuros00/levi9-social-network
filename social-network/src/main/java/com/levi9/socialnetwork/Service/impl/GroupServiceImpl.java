package com.levi9.socialnetwork.Service.impl;

import java.util.List;

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
	
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}
	
	public Group getGroupById(Long id) throws ResourceNotFoundException {
		return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group is not found for this id ::" + id));
	}

	public Group createGroup(Group group) {
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
	
}
