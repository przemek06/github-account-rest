package com.empik.githubaccountrest.service;

import com.empik.githubaccountrest.client.GithubClient;
import com.empik.githubaccountrest.dto.GithubUserDTO;
import com.empik.githubaccountrest.entity.LoginCount;
import com.empik.githubaccountrest.error.GithubUserNotFoundException;
import com.empik.githubaccountrest.model.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GithubUserService {

    private final GithubClient githubClient;
    private final LoginCountService loginCountService;
    private final Logger log = LoggerFactory.getLogger(GithubUserService.class);


    @Autowired
    public GithubUserService(GithubClient githubClient, LoginCountService loginCountService) {
        this.githubClient = githubClient;
        this.loginCountService = loginCountService;
    }

    @Transactional
    public GithubUserDTO getGithubUserByLogin(String login) throws GithubUserNotFoundException {
        if (login == null) {
            throw new GithubUserNotFoundException();
        }
        GithubUser githubUser = githubClient.getUserByLogin(login).orElseThrow(GithubUserNotFoundException::new);

        LoginCount loginCount = loginCountService.incrementLoginCount(githubUser.getLogin());
        log.info("Request count for user " + login + ": " + loginCount.getRequestCount());

        return mapGithubUserToDTO(githubUser);
    }

    private GithubUserDTO mapGithubUserToDTO(GithubUser githubUser) {
        Float calculations = null;
        if (githubUser.getFollowers() != null && githubUser.getFollowers() != 0 && githubUser.getPublicRepos() != null) {
            calculations = ((float) 6 / githubUser.getFollowers() * (2 + githubUser.getPublicRepos()));
        }

        return GithubUserDTO.builder()
                .id(githubUser.getId())
                .avatarUrl(githubUser.getAvatarUrl())
                .createdAt(githubUser.getCreatedAt())
                .login(githubUser.getLogin())
                .name(githubUser.getName())
                .type(githubUser.getType())
                .calculations(calculations)
                .build();
    }
}
