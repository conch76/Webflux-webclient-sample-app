package com.conch.reactive.rsotoken;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class CustomAccessTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        AbstractAuthenticationToken abstractAuthenticationToken = jwtAuthenticationConverter.convert(jwt);
        ApiAccessToken apiAccessToken = new ApiAccessToken(jwt, abstractAuthenticationToken.getAuthorities());
        return apiAccessToken;
    }
}
