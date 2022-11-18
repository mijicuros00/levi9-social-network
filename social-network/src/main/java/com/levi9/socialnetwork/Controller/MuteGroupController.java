package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Exception.BadRequestException;
import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Model.MuteDuration;
import com.levi9.socialnetwork.Model.MuteGroup;
import com.levi9.socialnetwork.Service.impl.MuteGroupServiceImpl;
import com.levi9.socialnetwork.dto.MuteGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mute_groups")
public class MuteGroupController {
    @Autowired
    private MuteGroupServiceImpl muteGroupService;


    @PutMapping("/users/{userId}/groups/{groupId}")
    public ResponseEntity<MuteGroupDTO> updateMuteGroup(@PathVariable(value = "userId") Long userId,
                                                        @PathVariable(value = "groupId") Long groupId,
                                                        @RequestBody String muteDurationName) throws ResourceExistsException, BadRequestException {
        MuteDuration muteDuration;
        try {
            muteDuration = MuteDuration.valueOf(muteDurationName);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid mute duration string.");
        }
        MuteGroup muteGroup = muteGroupService.updateMuteGroup(userId, groupId, muteDuration);
        return new ResponseEntity<>(new MuteGroupDTO(muteGroup), HttpStatus.OK);
    }
}
