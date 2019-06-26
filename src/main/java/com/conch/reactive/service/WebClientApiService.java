package com.conch.reactive.service;
import com.conch.reactive.domain.ApiResponse;
import com.conch.reactive.domain.Instruction;
import com.conch.reactive.domain.WebClientRequestContext;
import com.conch.reactive.domain.WebClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class WebClientApiService {

    private final WebClientApiClient webClientApiClient;

    public Mono<ApiResponse> sendPostRequest(WebClientRequestContext webClientRequestContext) {
        return webClientApiClient.sendPostRequestWithJsonBody(webClientRequestContext)
                .map(webClientResponse -> translateResponse(webClientResponse));
    }

    private ApiResponse translateResponse(WebClientResponse webClientResponse) {
        ApiResponse apiResponse = new ApiResponse();
        List<Instruction> instructions = webClientResponse.getInstructions();
        if (CollectionUtils.isEmpty(instructions)) {
            // no instruction..
            return apiResponse;
        }
        for (Instruction instruction : instructions) {
            // do something and add
            apiResponse.addInstruction(instruction);
        }
        return apiResponse;
    }
}
