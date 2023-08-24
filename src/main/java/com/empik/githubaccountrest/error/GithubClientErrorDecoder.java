package com.empik.githubaccountrest.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class GithubClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new GithubUserNotFoundException();
        }
        return new Exception(response.toString());
    }
}
