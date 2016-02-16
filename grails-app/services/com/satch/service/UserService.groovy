package com.satch.service

import com.satch.domain.User
import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils

@Transactional
class UserService {

    User createRandomUser(String provider, String accessToken) {
        String username = provider + "-" + accessToken
        String password = generatePassword(provider, accessToken)
        User user = new User(username: username, password: password, enabled: true)
        if(user.validate()) return user
        else return null
    }

    private String generatePassword(String provider, String accessToken){
        return RandomStringUtils.random(5, provider + accessToken) + RandomStringUtils.random(5, true, true);
    }
}
