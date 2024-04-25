package com.mysikabox.config.apiKey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class APIKeyService {


    private final APIKeyRepository repo;


    public APIKey findByToken(String token){
        return  repo.findByToken(token);
    }

    public APIKey save(APIKey user){
        return  repo.save(user);
    }

    public void delete(APIKey user){
         repo.delete(user);
    }



}
