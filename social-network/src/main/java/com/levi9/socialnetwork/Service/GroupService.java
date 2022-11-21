package com.levi9.socialnetwork.Service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.dto.GroupDTO;

public interface GroupService {

	public List<Group> getAllGroups();
	
	public Group getGroupById(Long id) throws ResourceNotFoundException;

	public Group createGroup(GroupDTO groupDTO);
	
	public Group updateGroup(Long groupId, @RequestBody GroupDTO groupDTO) throws ResourceNotFoundException;
	
	public Group deleteGroup(Long groupId) throws ResourceNotFoundException;

	public boolean acceptMember(Long userId, Long groupId) throws ResourceNotFoundException;
	
}
