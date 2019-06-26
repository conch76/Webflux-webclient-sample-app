package com.conch.reactive.service;

import com.conch.reactive.domain.WebClientRequestContext;
import com.conch.reactive.domain.WebClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Repository
@AllArgsConstructor
@Slf4j
public class WebClientApiClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public Mono<WebClientResponse> sendPostRequestWithJsonBody(WebClientRequestContext webClientRequestContext) {
        log.info("Calling post request with body {}", webClientRequestContext);

        return webClient.post().uri(uriBuilder -> uriBuilder.path("").build())
                .body(BodyInserters.fromObject(webClientRequestContext))
                .retrieve()
                .bodyToMono(String.class)
                .map(this::convertResponse);
    }

    private WebClientResponse convertResponse(String responseString) {
        log.info("Received instruction {}", responseString);
        WebClientResponse response;

        try {
            response = objectMapper.readValue(responseString, WebClientResponse.class);
        } catch (IOException e) {
            log.error("API has been changed, something is not right", e);
            response = new WebClientResponse();
        }

        return response;
    }
}
