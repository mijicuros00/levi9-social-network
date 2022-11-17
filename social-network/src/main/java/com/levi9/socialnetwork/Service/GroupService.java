package com.levi9.socialnetwork.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;
	
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}
	
	public Group getGroupById(Long id) {
		return groupRepository.getById(id);
	}

	public Group createGroup(Group group) {
		return groupRepository.save(group);
	}
	
	public Group updateGroup(Long groupId, @RequestBody Group groupDetails) {
		Group group = groupRepository.getById(groupId);
		group.setPrivate(group.isPrivate());
		
		return group;
	}
	
	public Group deleteGroup(Long groupId) throws ResourceNotFoundException{
		Group group = groupRepository.getById(groupId);
		groupRepository.delete(group);
		
		return group;
	}
}
