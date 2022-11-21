package com.levi9.socialnetwork.Service.impl;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.MuteDuration;
import com.levi9.socialnetwork.Model.MuteGroup;
import com.levi9.socialnetwork.Model.MuteGroupId;
import com.levi9.socialnetwork.Repository.MuteGroupRepository;
import com.levi9.socialnetwork.Service.MuteGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MuteGroupServiceImpl implements MuteGroupService {
    private static final String NOT_FOUND_MESSAGE = "MuteGroup not found for these ids :: ";
    private static final String ALREADY_EXISTS_MESSAGE = "MuteGroup already exists with these ids :: ";

    @Autowired
    private MuteGroupRepository muteGroupRepository;

    public List<MuteGroup> getAllMuteGroups() {
        return muteGroupRepository.findAll();
    }

    public MuteGroup getMuteGroupByIds(Long userId, Long groupId) throws ResourceNotFoundException {
        MuteGroupId muteGroupId = new MuteGroupId(userId, groupId);
        return muteGroupRepository.findById(muteGroupId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + muteGroupId));
    }

    public MuteGroup createMuteGroup(MuteGroup muteGroup) throws ResourceExistsException {
        Long userId = muteGroup.getUserId();
        Long groupId = muteGroup.getGroupId();
        MuteGroupId muteGroupId = new MuteGroupId(userId, groupId);
        if (userId != null && groupId != null && muteGroupRepository.existsById(muteGroupId)) {
            throw new ResourceExistsException(ALREADY_EXISTS_MESSAGE + muteGroupId);
        }
        return muteGroupRepository.save(muteGroup);
    }

    public MuteGroup updateMuteGroup(Long userId, Long groupId, MuteDuration muteDuration) throws ResourceExistsException {
        MuteGroupId muteGroupId = new MuteGroupId(userId, groupId);
        Boolean isPermanent = muteDuration.isPermanent();
        LocalDateTime endOfMute = LocalDateTime.now().plus(muteDuration.getDuration());

        MuteGroup muteGroup = muteGroupRepository.findById(muteGroupId).orElse(null);

        if (muteGroup != null) {
            muteGroup.setIsPermanent(isPermanent);
            muteGroup.setEndOfMute(endOfMute);
            return muteGroupRepository.save(muteGroup);
        }

        muteGroup = new MuteGroup(userId, groupId, isPermanent, endOfMute);
        return createMuteGroup(muteGroup);
    }

    public void deleteMuteGroup(Long userId, Long groupId) throws ResourceNotFoundException {
        MuteGroupId muteGroupId = new MuteGroupId(userId, groupId);
        MuteGroup muteGroup = muteGroupRepository.findById(muteGroupId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE + muteGroupId));

        muteGroupRepository.delete(muteGroup);
    }
}
