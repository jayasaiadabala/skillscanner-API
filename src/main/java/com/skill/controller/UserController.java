package com.skill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skill.entity.UserDetails;
import com.skill.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserService service;

    
    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody UserDetails user) {
        return service.insertData(user);
    }


    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<String> sendOtp(@PathVariable String email) {
        return service.sendOtp(email);
    }

    @PostMapping("/sendResetOtp/{email}")
    public ResponseEntity<String> sendResetOtp(@PathVariable String email) {
        return service.sendResetOtp(email);
    }

    @PostMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<String> verifyOtp(
            @PathVariable String email,
            @PathVariable String otp) {
    	return service.verifyOtp(email, otp);
    }


    @GetMapping("/allusers")
    public ResponseEntity<List<UserDetails>> getUsers() {
        return service.getAllUsers();
    }


    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<UserDetails> getUser(
            @PathVariable String email,
            @PathVariable String password) {
        return service.getUser(email, password);
    }


    @GetMapping("/user/{email}")
    public ResponseEntity<UserDetails> getUser(@PathVariable String email) {
        return service.getUser(email);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updation(@RequestBody UserDetails user) {
        return service.updateData(user);
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        return service.deleteUser(email);
    }


    @GetMapping("/mailToPartner/{partnerGmail}/{userGmail}")
    public ResponseEntity<String> mailToPartner(
    		@PathVariable String partnerGmail,
    		@PathVariable String userGmail) {
        return service.mailToPartner(partnerGmail, userGmail);
    }
}
