package com.conch.reactive.service;

import com.conch.reactive.domain.Instruction;
import com.conch.reactive.domain.WebClientRequestContext;
import com.conch.reactive.domain.WebClientResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WebClientApiClientTest {

    private static final String TEST_RESPONSE_DATA = "testme!";

    @Test
    public void should_expect_instruction_with_correct_response_data() throws JsonProcessingException {
        MockWebServer server = new MockWebServer(); // mocking webclient response

        WebClient mockWebClient = WebClient.create(server.url("/").toString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        WebClientApiClient webClientApiClient = new WebClientApiClient(mockWebClient, objectMapper);

        WebClientRequestContext webClientRequestContext = WebClientRequestContext.builder()
                .userId("test")
                .parameter(1)
                .build();

        // setup response mock data
        String mockResponseString = buildMockApiResponseString();
        server.enqueue(
                new MockResponse()
                        .setHeader("Content-Type", "text/string;charset=UTF-8")
                        .setResponseCode(200)
                        .setBody(mockResponseString));

        StepVerifier
                .create(webClientApiClient.sendPostRequestWithJsonBody(webClientRequestContext))
                .expectNextMatches(WebClientApiClientTest::verifyResponse)
                .expectComplete()
                .verify();
    }

    private String buildMockApiResponseString() throws JsonProcessingException {
        WebClientResponse response = new WebClientResponse();
        List<Instruction> mockInstructionList = new ArrayList<>();
        mockInstructionList.add(Instruction.builder().data(TEST_RESPONSE_DATA).message("hello").build());
        response.setInstructions(mockInstructionList);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }

    private static boolean verifyResponse(WebClientResponse webClientResponse) {
        return webClientResponse.getInstructions() != null && webClientResponse.getInstructions().size() == 1 && webClientResponse.getInstructions().get(0).getData().equals(TEST_RESPONSE_DATA);
    }
}
