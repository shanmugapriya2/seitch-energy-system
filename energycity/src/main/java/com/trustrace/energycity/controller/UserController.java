package com.trustrace.energycity.controller;

import com.trustrace.energycity.controller.Response.APIResponse;
import com.trustrace.energycity.pojo.User;
import com.trustrace.energycity.pojo.UserLogin;
import com.trustrace.energycity.repository.UserRepository;
import com.trustrace.energycity.security.JwtUtility;
import com.trustrace.energycity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody User user){
        APIResponse response=new APIResponse ();
        response.setStatus ("Success");
        try {
            response.setData (userService.createUser(user));
        }catch (RuntimeException e){
            response.setMessage (e.getMessage ());
        }
        return new ResponseEntity<> (response, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@Valid @RequestBody UserLogin userLogin) {
        APIResponse response = new APIResponse();
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            response.setData(userService.login(userLogin));
            String jwtToken = jwtUtility.generateToken(userLogin.getEmailId(), 10*60*60);
            responseHeader.set("JWTToken", jwtToken);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, responseHeader, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllUser(@RequestHeader(value = "authorization") String auth){
        APIResponse response=new APIResponse ();
        if (Objects.equals(jwtUtility.validateToken (auth),"admin")){
            response.setStatus ("Success");
            response.setData (userService.getAllUser());
            response.setMessage ("User data Successfully");
            return new ResponseEntity<> (response,HttpStatus.OK);
        }
        response.setMessage ("No access");
        return new ResponseEntity<> (response,HttpStatus.UNAUTHORIZED);
    }
}


