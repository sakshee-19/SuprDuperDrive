package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CredentialsService {
    Logger logger = LoggerFactory.getLogger(CredentialsMapper.class);
    @Autowired
    private CredentialsMapper credentialsMapper;

    @Autowired
    private EncryptionService encryptionService;

    public List<CredentialsForm> findAllCredentials() {
        logger.info("**START** getAllCredentials");
        List<Credentials> credentialsList = credentialsMapper.findAllCredentials();
        List<CredentialsForm> credentialsFormsList = new ArrayList<>();
        for(Credentials cred : credentialsList) {
            CredentialsForm form = new CredentialsForm();
            form.setCredentialId(cred.getCredentialId());
            form.setDisplayPassword(encryptionService.decryptValue(cred.getPassword(), cred.getKey()));
            form.setPassword(cred.getPassword());
            form.setUrl(cred.getUrl());
            form.setUserId(cred.getUserId());
            form.setUsername(cred.getUsername());

            credentialsFormsList.add(form);
        }
        logger.info("**END** getAllCredentials ");
        return credentialsFormsList;
    }

    public Credentials findCredentialById(Integer credentialId) {
        logger.info("**START** findCredentialById id={}", credentialId);
        Credentials credentials = credentialsMapper.getCredentialsById(credentialId);
        String key = credentials.getKey();
        String decryptPass = encryptionService.decryptValue(credentials.getPassword(), key);
        credentials.setPassword(decryptPass);
        logger.info("**END** findCredentialById credentials={}", credentials);
        return credentials;
    }

    public int addNewCredentials(Credentials credentials) {
        logger.info("**START** addNewCredentials ={}", credentials);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encrptPass = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
        credentials.setPassword(encrptPass);
        credentials.setKey(encodedKey);
        logger.info("Storing credentials in DB ={}", credentials);
        int added = credentialsMapper.uploadCredentials(credentials);
        logger.info("**END** addNewCredentials");
        return added;
    }

    public int editCredentials(Credentials credentials, Integer credId) {
        logger.info("**START** editCredentials credNewVal={}, credId={}", credentials, credId);
        Credentials dbData = credentialsMapper.getCredentialsById(credId);
        if (dbData == null){
            logger.info("Credentials not found");
            return -1;
        }
        dbData.setUrl(credentials.getUrl());
        dbData.setUsername(credentials.getUsername());
        dbData.setPassword(encryptionService.encryptValue(credentials.getPassword(), dbData.getKey()));
        dbData.setUserId(credentials.getUserId());
        logger.info("Updating DB with updated values={}", dbData);
        credentialsMapper.updateCredentials(dbData);
        logger.info("**END** editCredentials");
        return 1;
    }

    public int deleteCredentials(Integer credId) {
        logger.info("**START** deleteCredentials");
        try {
            credentialsMapper.deleteCredentials(credId);
            return 1;
        } catch (NoSuchElementException e){
            logger.info("NO ELEMENT FOUND {}", e);
        }
        logger.info("**END** deleteCredentials");
        return -1;
    }
}
