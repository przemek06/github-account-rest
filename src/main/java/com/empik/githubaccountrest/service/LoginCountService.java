package com.empik.githubaccountrest.service;

import com.empik.githubaccountrest.entity.LoginCount;
import com.empik.githubaccountrest.repository.LoginCountRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginCountService {

    private final LoginCountRepository loginCountRepository;

    @Autowired
    public LoginCountService(LoginCountRepository loginCountRepository) {
        this.loginCountRepository = loginCountRepository;
    }

    @Transactional
    public LoginCount incrementLoginCount(String login) {
        LoginCount loginCount;
        Optional<LoginCount> loginCountOptional = loginCountRepository.getByLogin(login);

        if (loginCountOptional.isEmpty()) {
            loginCount = new LoginCount();
            loginCount.setLogin(login);
        } else {
            loginCount = loginCountOptional.get();
        }

        loginCount.setRequestCount(loginCount.getRequestCount() + 1);
        return loginCountRepository.save(loginCount);
    }
}
