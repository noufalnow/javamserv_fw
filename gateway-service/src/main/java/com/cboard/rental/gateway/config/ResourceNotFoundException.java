package com.cboard.rental.gateway.config;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private Object resourceId;

    public ResourceNotFoundException(String message, String resourceName, Object resourceId) {
        super(message);
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super("Resource not found");
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Object getResourceId() {
        return resourceId;
    }
}
