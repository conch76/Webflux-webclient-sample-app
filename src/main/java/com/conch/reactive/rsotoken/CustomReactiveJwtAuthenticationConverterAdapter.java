package com.conch.reactive.rsotoken;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class CustomReactiveJwtAuthenticationConverterAdapter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private final Converter<Jwt, AbstractAuthenticationToken> delegate;

    public CustomReactiveJwtAuthenticationConverterAdapter(Converter<Jwt, AbstractAuthenticationToken> delegate) {
        Assert.notNull(delegate, "delegate cannot be null");
        this.delegate = delegate;
    }

    public final Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return Mono.just(jwt).map(this.delegate::convert);
    }
}
