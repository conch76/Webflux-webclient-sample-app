package com.conch.reactive.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
@AllArgsConstructor
public class WebClientConfiguration {

    private final ApiConfiguration apiConfiguration;

    @Bean
    public WebClient apiWebClient(WebClient.Builder builder) {
        return builder
                .uriBuilderFactory(uriBuilderFactory(apiConfiguration.getBaseUri()))
                .clientConnector(clientHttpConnector(apiConfiguration.getConnectTimeoutInMilliSecond(), apiConfiguration.getReadTimeoutInSecond()))
                .build();
    }

    private DefaultUriBuilderFactory uriBuilderFactory(String baseUri) {
        // see URI_COMPONENT : https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#web-uri-encoding
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(baseUri);
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        return defaultUriBuilderFactory;
    }

    private ClientHttpConnector clientHttpConnector(int connectTimeoutInMilliSecond, int readTimeoutInSecond) {
        TcpClient tcpClient =
                TcpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutInMilliSecond)
                        .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(readTimeoutInSecond)));
        return new ReactorClientHttpConnector(HttpClient.from(tcpClient));
    }
}
