package com.empik.githubaccountrest.controller;

import com.empik.githubaccountrest.dto.GithubUserDTO;
import com.empik.githubaccountrest.error.GithubUserNotFoundException;
import com.empik.githubaccountrest.service.GithubUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GithubUserController {

    private final GithubUserService githubUserService;
    private final Logger log = LoggerFactory.getLogger(GithubUserController.class);

    @Autowired
    public GithubUserController(GithubUserService githubUserService) {
        this.githubUserService = githubUserService;
    }

    @GetMapping(value = "/users/{username}", produces = "application/json")
    public ResponseEntity<GithubUserDTO> getGithubUserByLogin(@PathVariable String username) throws GithubUserNotFoundException {
        return ResponseEntity.ok(githubUserService.getGithubUserByLogin(username));
    }

    @ExceptionHandler({GithubUserNotFoundException.class})
    public ResponseEntity<String> handleGithubUserNotFound(GithubUserNotFoundException ex) {
        log.error("", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
