package com.mysikabox.config.apiKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.mysikabox.config.util.RecordNotFoundException;
import com.mysikabox.config.util.ResponseObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Slf4j
public class APIKeyController {

    private final APIKeyService service;


    @PostMapping("/verifyToken")
    public Authenticated authenticate(@RequestBody TokenAuthenticationRequest request){
        try{
            APIKey apiKey = service.findByToken(request.getToken());
            if(apiKey!=null){
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWTRequest = jwtVerifier.verify(request.getToken());
                String requestUsername = decodedJWTRequest.getSubject();
                DecodedJWT decodedJWT = jwtVerifier.verify(apiKey.getToken());
                String username = decodedJWT.getSubject();

                if(requestUsername != null && requestUsername.equals(username)) {
                    return new Authenticated(true);
                }else {
                    log.info("invalid auth");
                }
            }
        }catch (Exception e){
            log.error("error verifying token: {}",e.getMessage());
        }
        return new Authenticated(false);
    }



    @GetMapping("/token/{token}")
    public ResponseEntity<?> findByToken(@PathVariable("token") String token){
        APIKey apiKey = service.findByToken(token);
        if(apiKey==null){
            throw new RecordNotFoundException("No token found for: "+token);
        }
        ResponseObject object = new ResponseObject(HttpStatus.OK.value(),"Success",apiKey);
        return new ResponseEntity<>(object, HttpStatus.OK);
    }



    @PostMapping("/createToken")
    public ResponseEntity<?> createToken(@RequestBody APIKeyRequest request){
        APIKey apiKey = new APIKey();
        apiKey.setOrganizationCode(request.getOrganizationCode());
        apiKey.setOrganizationName(request.getOrganizationName());
        apiKey.setGeneratedOn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        apiKey.setGeneratedBy(request.getGeneratedBy());
        apiKey.setValidDays(request.getValidDays());

        Calendar expirationDate = Calendar. getInstance();
        expirationDate.add(Calendar.DAY_OF_MONTH,request.getValidDays());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(request.getOrganizationCode())
                .withExpiresAt(expirationDate.getTime())
                .withIssuer("Sys_Admin")
                .sign(algorithm);

        apiKey.setToken(access_token);
        apiKey.setExpirationDate(expirationDate.getTime().toString());

        APIKey savedApiKey = service.save(apiKey);

        ResponseObject object = new ResponseObject(HttpStatus.CREATED.value(),"Token successfully created",savedApiKey);
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    @PostMapping("/deleteToken")
    public ResponseEntity<?> deleteUser(@RequestBody APIKey user){
        service.delete(user);
        return new ResponseEntity<>( HttpStatus.OK);
    }



}
