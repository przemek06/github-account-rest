package com.empik.githubaccountrest.client;

import com.empik.githubaccountrest.error.GithubClientErrorDecoder;
import com.empik.githubaccountrest.model.GithubUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "github", url = "https://api.github.com/", configuration = { GithubClientErrorDecoder.class })
public interface GithubClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}", produces = "application/json")
    Optional<GithubUser> getUserByLogin(@PathVariable String username);

}