package com.mysikabox.config.apiKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIKeyRepository extends JpaRepository<APIKey, String> {

    APIKey findByOrganizationCode(String organizationCode);
    APIKey findByToken(String token);

}
