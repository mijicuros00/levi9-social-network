package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Service.RegistrationService;
import com.levi9.socialnetwork.dto.RegistrationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@RequestBody RegistrationRequestDTO registrationRequestDTO){
        System.out.println(registrationRequestDTO);
        try{
            registrationService.register(registrationRequestDTO);
        }catch (IllegalStateException illegalStateException){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
