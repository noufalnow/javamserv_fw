package com.cboard.rental.gateway.config;

import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;

public class CustomExceptionConverter extends ThrowableProxyConverter {

    @Override
    public String convert(ILoggingEvent event) {
        ThrowableProxy throwableProxy = (ThrowableProxy) event.getThrowableProxy();
        if (throwableProxy != null) {
            StringBuilder output = new StringBuilder();
            // Get the stack trace elements
            StackTraceElement[] stackTrace = throwableProxy.getThrowable().getStackTrace();
            
            for (StackTraceElement element : stackTrace) {
                String className = element.getClassName();
                // Exclude certain package traces (adjust as necessary)
                if (className.startsWith("com.cboard.rental.gateway") || className.startsWith("java.base")) {
                    output.append(element.toString()).append(System.lineSeparator());
                }
            }
            return output.toString(); // Return the formatted output
        }
        return "";
    }
}
