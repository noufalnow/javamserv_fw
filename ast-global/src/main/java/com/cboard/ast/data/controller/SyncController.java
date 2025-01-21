package com.cboard.ast.data.controller;

import com.cboard.ast.data.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    /**
     * Endpoint to trigger the syncData method manually.
     *
     * @return ResponseEntity with a success message.
     */
    @GetMapping("/trigger")
    public ResponseEntity<String> triggerSync() {
        try {
            syncService.syncData();
            return ResponseEntity.ok("Synchronization triggered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error triggering synchronization: " + e.getMessage());
        }
    }
}
