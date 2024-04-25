package com.mysikabox.config.filter;

import com.mysikabox.config.apiKey.APIKeyController;
import com.mysikabox.config.apiKey.Authenticated;
import com.mysikabox.config.apiKey.TokenAuthenticationRequest;
import com.mysikabox.config.util.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    private final APIKeyController apiKeyController;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//        String gateWayAccess = exchange.getRequest().getHeaders().getFirst("gateWayAccess");
//        log.info("gateWayAccess header: {}",gateWayAccess);


        //First check for no auth paths. i.e. paths that need no authentication
        String noAuthPaths = exchange.getRequest().getPath().toString();
        log.info("noAuthPaths url: {}",noAuthPaths);
        if(noAuthPaths!=null){
            if(noAuthPaths.equals("/authentication/createToken")
                    || noAuthPaths.equals("/authentication/all")

                    || noAuthPaths.equals("/user/createUser")
                    || noAuthPaths.equals("/user/fetchUserById/**")
                    || noAuthPaths.equals("/user/fetchAllUsers")
                    || noAuthPaths.equals("/user/searchName")

                    || noAuthPaths.equals("/tasks/fetchTaskById/**")
                    || noAuthPaths.equals("/tasks/fetchAllTask")

            ) {
                return chain.filter(exchange);
            }
        }

        if((!isAuthorized(authorizationHeader))){
            throw new UnauthorizedException("Please provide valid authorization token before access can be granted");
        }

        return chain.filter(exchange);

    }


    private boolean isAuthorized(String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring("Bearer ".length());
            TokenAuthenticationRequest tokenAuthenticationRequest = new TokenAuthenticationRequest(token);
            Authenticated auth = apiKeyController.authenticate(tokenAuthenticationRequest);
            if(auth != null && auth.isAuthenticated()){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
