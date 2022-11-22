package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.dto.RegistrationRequestDTO;

public interface RegistrationService {

    String register(RegistrationRequestDTO registrationRequestDTO);
    String confirmToken(String token) throws ResourceNotFoundException;
}
