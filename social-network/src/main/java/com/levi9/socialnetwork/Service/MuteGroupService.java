package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.MuteDuration;
import com.levi9.socialnetwork.Model.MuteGroup;

import java.util.List;

public interface MuteGroupService {
    public List<MuteGroup> getAllMuteGroups();
    public MuteGroup getMuteGroupByIds(Long userId, Long groupId) throws ResourceNotFoundException;
    public MuteGroup createMuteGroup(MuteGroup muteGroup) throws ResourceExistsException;
    public MuteGroup updateMuteGroup(Long userId, Long groupId, MuteDuration muteDuration) throws ResourceExistsException, ResourceNotFoundException;
    public void deleteMuteGroup(Long userId, Long groupId) throws ResourceNotFoundException;
}
