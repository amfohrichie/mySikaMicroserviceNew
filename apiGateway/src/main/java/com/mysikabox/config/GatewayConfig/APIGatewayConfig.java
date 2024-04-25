package com.mysikabox.config.GatewayConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class APIGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return getDevRouteLocator(builder);
    }

    private RouteLocator getDevRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/user/**").uri("http://localhost:8020"))
                .route(p -> p.path("/tasks/**").uri("http://localhost:8027"))
                .build();
    }

}
