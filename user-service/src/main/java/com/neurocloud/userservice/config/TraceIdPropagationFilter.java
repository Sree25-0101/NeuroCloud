package com.neurocloud.userservice.config;

import com.neurocloud.common.logging.TraceIdContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TraceIdPropagationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TraceIdPropagationFilter.class);

    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId != null && !traceId.isEmpty()) {
            TraceIdContext.setTraceId(traceId);
        }
        log.debug("Received X-Trace-Id: " + traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            TraceIdContext.clear(); // Clean up ThreadLocal after request
        }
    }
}
