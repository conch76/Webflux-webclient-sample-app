package com.conch.reactive.token;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
public class ApiAccessToken extends JwtAuthenticationToken {
    private final GenericAccount genericAccount;

    public ApiAccessToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);

        // Allow any runtime exception as valid token should not throw exception
        String subject = jwt.getSubject();
        genericAccount = GenericAccount.builder().authId(subject).build();
    }
}
