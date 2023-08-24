package com.empik.githubaccountrest.service;

import com.empik.githubaccountrest.client.GithubClient;
import com.empik.githubaccountrest.dto.GithubUserDTO;
import com.empik.githubaccountrest.entity.LoginCount;
import com.empik.githubaccountrest.error.GithubUserNotFoundException;
import com.empik.githubaccountrest.model.GithubUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(SpringRunner.class)
class GithubUserServiceTest {

    private final static String VALID_LOGIN = "valid_login_1";
    private final static String VALID_LOGIN_NO_FOLLOWERS = "valid_login_2";
    private final static String INVALID_LOGIN = "invalid_login";
    private final static int FOLLOWERS = 6;
    private final static int PUBLIC_REPOS = 17;
    private final static float EXPECTED_CALCULATION_VALUE = 19.0f;

    @InjectMocks
    GithubUserService githubUserService;

    @Mock
    GithubClient githubClient;

    @Mock
    LoginCountService loginCountService;

    @BeforeEach
    public void setup(){
        //given
        MockitoAnnotations.openMocks(this);

        Mockito.when(githubClient.getUserByLogin(VALID_LOGIN))
                .thenReturn(Optional.of(setupValidUser()));
        Mockito.when(loginCountService.incrementLoginCount(VALID_LOGIN))
                        .thenReturn(new LoginCount());

        Mockito.when(githubClient.getUserByLogin(VALID_LOGIN_NO_FOLLOWERS))
                .thenReturn(Optional.of(setupValidUserNoFollowers()));
        Mockito.when(loginCountService.incrementLoginCount(VALID_LOGIN_NO_FOLLOWERS))
                .thenReturn(new LoginCount());

        Mockito.when(githubClient.getUserByLogin(INVALID_LOGIN))
                .thenReturn(Optional.empty());
    }

    private GithubUser setupValidUser() {
        GithubUser validUser = new GithubUser();
        validUser.setLogin(VALID_LOGIN);
        validUser.setFollowers(FOLLOWERS);
        validUser.setPublicRepos(PUBLIC_REPOS);
        return validUser;
    }

    private GithubUser setupValidUserNoFollowers() {
        GithubUser validUser = new GithubUser();
        validUser.setLogin(VALID_LOGIN);
        validUser.setFollowers(0);
        validUser.setPublicRepos(PUBLIC_REPOS);
        return validUser;
    }

    @Test
    public void whenExistingLoginThenCalculatedUserShouldBeReturned() throws GithubUserNotFoundException {
        // when
        GithubUserDTO githubUserDTO = githubUserService.getGithubUserByLogin(VALID_LOGIN);

        //then
        Assert.assertEquals(githubUserDTO.getLogin(), VALID_LOGIN);
        Assert.assertEquals(githubUserDTO.getCalculations(), Float.valueOf(EXPECTED_CALCULATION_VALUE));
    }

    @Test
    public void whenExistingLoginWithNoFollowersThenNullCalculationUserShouldBeReturned() throws GithubUserNotFoundException {
        // when
        GithubUserDTO githubUserDTO = githubUserService.getGithubUserByLogin(VALID_LOGIN_NO_FOLLOWERS);

        //then
        Assert.assertEquals(githubUserDTO.getLogin(), VALID_LOGIN);
        Assert.assertNull(githubUserDTO.getCalculations());
    }


    @Test
    public void whenNonExistingLoginThenThrowException() throws GithubUserNotFoundException {

        // when
        Exception exception = assertThrows(GithubUserNotFoundException.class, () -> {
            GithubUserDTO githubUserDTO = githubUserService.getGithubUserByLogin(INVALID_LOGIN);
        });

        // then
        String expectedMessage = "Specified Github user doesn't exist.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

}