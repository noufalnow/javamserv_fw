package com.cboard.ast.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhpSyncService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Use ObjectMapper for raw JSON serialization

    /**
     * Sends data to a PHP server for synchronization.
     *
     * @param tableName  the name of the table being synchronized.
     * @param primaryKey the primary key column name.
     * @param rows       the rows of data to synchronize.
     * @return a list of successfully synchronized primary key IDs.
     */
    public List<Long> sendToPhpServer(String tableName, String primaryKey, List<Map<String, Object>> rows) {
        String url = "http://localhost/phpsynch.php";

        // Prepare the payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("table", tableName);
        payload.put("primaryKey", primaryKey);
        payload.put("rows", rows);

        //rows.forEach(row -> System.out.println("Fetched row: " + row));

        try {
            // Convert the payload to raw JSON
            String rawJson = objectMapper.writeValueAsString(payload);

            // Send the raw JSON as the request body
            HttpEntity<String> request = new HttpEntity<>(rawJson);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                return (List<Long>) responseBody.get("successfulIds"); // Ensure PHP sends this key
            } else {
                throw new RuntimeException("API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error sending data to PHP server: " + e.getMessage(), e);
        }
    }
}
