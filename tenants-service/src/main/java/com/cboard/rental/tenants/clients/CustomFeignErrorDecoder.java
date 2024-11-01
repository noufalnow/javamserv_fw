/*package com.cboard.rental.tenants.clients;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import com.cboard.rental.tenants.config.ResourceNotFoundException;

@Component
public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new IllegalArgumentException("Bad Request");
            case 404:
                // Extracting resource information from methodKey and response if needed
                String resourceName = "Property"; // Assuming it's a property for now; can be extracted dynamically if needed
                String message = "Resource not found";
                Object resourceId = extractResourceIdFromMethodKey(methodKey); // Assuming a method to extract resource ID

                return new ResourceNotFoundException(message, resourceName, resourceId);
            case 500:
                return new RuntimeException("Internal Server Error - Property service encountered an error");
            default:
                return new Exception("Generic error");
        }
    }

    private Object extractResourceIdFromMethodKey(String methodKey) {
        // Implement logic to extract the resource ID from the methodKey if possible.
        // This is a placeholder function. You may parse methodKey or create a custom mapping.
        return null; // Replace with actual ID extraction logic if applicable
    }
}
*/
