package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Service.RegistrationService;
import com.levi9.socialnetwork.dto.RegistrationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public ResponseEntity<Void> registration(@RequestBody RegistrationRequestDTO registrationRequestDTO)
            throws IOException {

        registrationService.register(registrationRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam String token) throws ResourceNotFoundException {

        return registrationService.confirmToken(token);
    }
}
