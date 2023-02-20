package com.trustrace.energycity.controller;
import com.trustrace.energycity.controller.Response.APIResponse;
import com.trustrace.energycity.security.JwtUtility;
import com.trustrace.energycity.pojo.Provider;
import com.trustrace.energycity.service.ProviderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;
    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping
    public ResponseEntity<APIResponse> create(@Valid @RequestBody Provider provider,@RequestHeader(value="authorization") String auth){
        APIResponse response=new APIResponse ();
        try {
            if (Objects.equals (jwtUtility.validateToken (auth),"admin")){
                response.setStatus ("Successfully");
                response.setData (providerService.createProvider(provider));
                return new ResponseEntity<> (response, HttpStatus.OK);
            }
        }catch (Exception e){
            response.setMessage (e.getMessage ());
        }
        response.setMessage ("Access denied");
        return new ResponseEntity<> (response,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllProvider(){
        APIResponse response=new APIResponse ();
        response.setStatus ("Successfully");
        response.setData (providerService.getAllProvider());
        response.setMessage ("Provider data Successfully");
        return new ResponseEntity<> (response,HttpStatus.OK);
    }

    @PostMapping("/change status")
    public ResponseEntity<APIResponse> changeStatus(@PathParam("name") String name, @PathParam("status") String status,
                                                    @RequestHeader(value = "authorization") String auth) {
        APIResponse response = new APIResponse();
        try {
            if (Objects.equals(jwtUtility.validateToken(auth), "admin")) {
                response.setStatus("Successfully");
                response.setData(providerService.changeStatus(name, status));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        response.setMessage("Access denied");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}

