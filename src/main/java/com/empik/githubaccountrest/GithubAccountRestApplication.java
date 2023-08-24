package com.empik.githubaccountrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GithubAccountRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubAccountRestApplication.class, args);
    }

}
