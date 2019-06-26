package com.conch.reactive.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.function.Predicate;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfiguration {
    @Value("${server.port}")
    private int port;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.conch.reactive"))
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .host(String.format("localhost:%d", port))
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sample reactive backend")
                .contact(new Contact("Some dev Team", "", "bro@email.com"))
                .version("1.0")
                .build();
    }

    private Predicate<String> paths() {
        return PathSelectors.regex(".*");
    }

}
