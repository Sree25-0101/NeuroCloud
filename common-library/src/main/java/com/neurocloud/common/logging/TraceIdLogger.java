package com.neurocloud.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceIdLogger {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdLogger.class);

    public static void info(String message) {
        String traceId = TraceIdContext.getTraceId();
        logger.info("[TRACE_ID: {}] {}", traceId != null ? traceId : "UNKNOWN", message);
    }

    public static void error(String message) {
        String traceId = TraceIdContext.getTraceId();
        logger.error("[TRACE_ID: {}] {}", traceId != null ? traceId : "UNKNOWN", message);
    }

    public static void debug(String message) {
        String traceId = TraceIdContext.getTraceId();
        logger.debug("[TRACE_ID: {}] {}", traceId != null ? traceId : "UNKNOWN", message);
    }
}
