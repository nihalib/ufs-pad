package com.bluemoonllc.ufs.api.client;

import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class FallbackLogCreator {
    private final StringBuilder errorStringBuilder = new StringBuilder("Error while executing ");

    private String className;
    private String methodName;
    private final Map<String, Object> parameters = new HashMap<>();
    private Throwable error;

    public FallbackLogCreator setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public FallbackLogCreator setClassName(String className) {
        this.className = className;
        return this;
    }

    public FallbackLogCreator setError(Throwable error) {
        this.error = error;
        return this;
    }

    public FallbackLogCreator addParameter(String parameterName, Object parameterValue) {
        this.parameters.put(parameterName, parameterValue);
        return this;
    }

    public String buildLogEntry() {
        errorStringBuilder.append(className).append("#").append(methodName);
        if (!parameters.isEmpty()) {
            errorStringBuilder.append(" with parameters: ");
            parameters.forEach((name, value) -> errorStringBuilder
                    .append(name)
                    .append('=')
                    .append(ObjectUtils.defaultIfNull(value, "null"))
                    .append(", "));
            errorStringBuilder.delete(errorStringBuilder.lastIndexOf(","), errorStringBuilder.length());
        }
        if (nonNull(error)) {
            errorStringBuilder.append(", cause: ").append(error.toString()).append(": ").append(error.getMessage());
        }
        return errorStringBuilder.toString();
    }
}
