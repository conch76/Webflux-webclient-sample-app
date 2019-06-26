package com.conch.reactive.service;

import com.conch.reactive.domain.ApiRequest;
import com.conch.reactive.domain.WebClientRequestContext;
import com.conch.reactive.token.ApiAccessToken;
import org.springframework.stereotype.Service;

@Service
public class RequestAuthTokenParser {
    public WebClientRequestContext createWebClientRequestContext(ApiAccessToken apiAccessToken, ApiRequest apiRequest) {
        String authId = apiAccessToken.getGenericAccount().getAuthId();

        return WebClientRequestContext.builder()
                .userId(authId)
                .parameter(apiRequest.getIntParameter())
                .build();
    }
}
