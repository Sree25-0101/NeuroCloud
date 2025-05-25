package com.neurocloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(1) // Higher priority (lower number = higher priority)
public class TraceIdFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(TraceIdFilter.class);

    public static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = UUID.randomUUID().toString();

        // Mutate request to include header
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(exchange.getRequest()
                        .mutate()
                        .header(TRACE_ID_HEADER, traceId)
                        .build())
                .build();

        // Optional log
        log.debug("[Gateway] Generated TraceId: " + traceId);

        log.debug("[Gateway] Request headers after mutation:");
        mutatedExchange.getRequest().getHeaders().forEach((key, value) ->
                System.out.println(key + ": " + value));

        return chain.filter(mutatedExchange);
    }

}
