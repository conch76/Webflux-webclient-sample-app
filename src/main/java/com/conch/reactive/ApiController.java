package com.conch.reactive;

import com.conch.reactive.domain.ApiRequest;
import com.conch.reactive.domain.ApiResponse;
import com.conch.reactive.domain.WebClientRequestContext;
import com.conch.reactive.rsotoken.ApiAccessToken;
import com.conch.reactive.service.RequestAuthTokenParser;
import com.conch.reactive.service.WebClientApiService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ApiController {
    private final RequestAuthTokenParser requestAuthTokenParser;
    private final WebClientApiService webClientApiService;

    @PostMapping("/application")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization header (eg. Bearer token string comes here)",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @ApiOperation(value = "Test API with bearer token header requirements", response = ApiResponse.class)
    public Mono<ApiResponse> handleAppEvent(
            @AuthenticationPrincipal ApiAccessToken apiAccessToken,
            @ApiParam(value = "Sample application request body data") @RequestBody ApiRequest apiRequest) {
        WebClientRequestContext webClientRequestContext = requestAuthTokenParser.createWebClientRequestContext(apiAccessToken, apiRequest);
        return webClientApiService.sendPostRequest(webClientRequestContext);
    }
}
