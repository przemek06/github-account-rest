package com.empik.githubaccountrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubUserDTO {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Date createdAt;
    private Float calculations;
}
