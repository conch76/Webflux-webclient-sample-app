/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.conch.reactive.configuration;

import com.conch.reactive.token.CustomReactiveJwtAuthenticationConverterAdapter;
import com.conch.reactive.token.CustomAccessTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebFluxSecurity
public class SecurityConfiguration {
	@Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.cors().and().csrf().disable()
                .authorizeExchange().pathMatchers(permitAllPatterns()).permitAll()
                .and().authorizeExchange().anyExchange().authenticated()
                .and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
        return http.build();
    }

    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
	    return new CustomReactiveJwtAuthenticationConverterAdapter(new CustomAccessTokenConverter());
    }

    private String[] permitAllPatterns() {
        List<String> list = new ArrayList<>();

        list.add(healthPattern());
        list.addAll(swaggerPatterns());

        return list.toArray(new String[list.size()]);
    }

    private String healthPattern() {
        return "/health";
    }

    private List<String> swaggerPatterns() {
        return Arrays.asList("/swagger*/**", "/webjars*/**", "/v2/api-docs");
    }
}
