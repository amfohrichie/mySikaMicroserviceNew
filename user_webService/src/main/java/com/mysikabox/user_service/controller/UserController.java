package com.mysikabox.user_service.controller;

import com.mysikabox.user_service.Handlers.ResponseHandler;
import com.mysikabox.user_service.entities.User;
import com.mysikabox.user_service.repository.UserRepository;
import com.mysikabox.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

    final UserRepository userRepository;
    final UserService userService;


    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user){

        User userModel = userRepository.findUserByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        if(userModel!=null){
            return ResponseHandler.handleResponse("User already exist", HttpStatus.OK,null);
        }
        try{
            userService.createUser(user);
            return ResponseHandler.handleResponse("User successfully created", HttpStatus.OK,user);

        }catch(Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


    @GetMapping("/fetchUserById/{id}")
    public ResponseEntity<?> fetchUserById(@PathVariable Long id){
        try{
            Optional<User> allUsers = userService.getUserById(id);
            return ResponseHandler.handleResponse("Successfully fetched all users",HttpStatus.OK,allUsers);

        }catch(Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


    @GetMapping("/fetchAllUsers")
    public ResponseEntity<?> fetchAllUsers(){
        try{
            List<User> allUsers = userService.getAllUsers();
            return ResponseHandler.handleResponse("Successfully fetched all users",HttpStatus.OK,allUsers);

        }catch(Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/searchName")
    public ResponseEntity<?> searchUsersByName(@RequestParam String firstName) {
        User users = userService.searchUsersByName(firstName);

        System.out.println("THE USER FOUND : "+users);
        return ResponseEntity.ok(users);
    }


    @PutMapping("/updateUserDetails/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id ,@RequestBody User user){
        Optional<User> userModel = userRepository.findById(id);
        if(userModel.isEmpty()){
            return ResponseHandler.handleResponse("User does not exist", HttpStatus.OK,null);
        }
        try {
                user.setId(user.getId());
                user.setFirstName(user.getFirstName());
                user.setLastName(user.getLastName());
                user.setDateOfBirth(user.getDateOfBirth());

                User editedUser = userService.updateUserDetails(id, user);
                if(editedUser!=null){
                    return ResponseHandler.handleResponse("Successfully updated user details", HttpStatus.OK,editedUser);
                }else{
                    return ResponseHandler.handleResponse("User with Id does not exist", HttpStatus.BAD_REQUEST,null);
                }

        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseHandler.handleResponse("Successfully deleted user", HttpStatus.OK,null);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


}
