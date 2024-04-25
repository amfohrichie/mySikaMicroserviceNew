package com.mysikabox.user_service.service;

import com.mysikabox.user_service.entities.User;
import com.mysikabox.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User searchUsersByName(String name) {
        return userRepository.findUserByFirstNameContainingIgnoreCase(name);
    }

    public User updateUserDetails(Long id,User updatedUser) {
         return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
