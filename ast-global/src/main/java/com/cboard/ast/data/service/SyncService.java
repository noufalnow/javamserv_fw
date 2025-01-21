package com.cboard.ast.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SyncService {

    @Autowired
    private TableMetadataService tableMetadataService;

    @Autowired
    private PhpSyncService phpSyncService;

    @Autowired
    private JdbcSyncService jdbcSyncService;

    /**
     * Scheduled method to synchronize data every 5 minutes.
     */
    @Scheduled(cron = "0 0 0/5 * * ?") // Runs every 5 minutes
    public void syncData() {
        // Fetch table metadata (table names and primary keys)
        List<Map<String, Object>> tables = tableMetadataService.fetchTableMetadata();

        if (tables.isEmpty()) {
            System.out.println("No tables found for synchronization.");
            return;
        }

        for (Map<String, Object> table : tables) {
            String tableName = (String) table.get("table_name");
            String primaryKey = (String) table.get("primary_key");

            try {
                // Fetch unsynced records from the table
                List<Map<String, Object>> rows = jdbcSyncService.fetchUnsyncedRecords(tableName, primaryKey);

                if (!rows.isEmpty()) {
                    // Send data to the PHP server and get successfully synced IDs
                    List<Long> successfulIds = phpSyncService.sendToPhpServer(tableName, primaryKey, rows);

                    // Update the sync_flag for successfully synced rows
                    jdbcSyncService.updateSyncFlag(tableName, primaryKey, successfulIds);

                    System.out.printf("Table: %s, Rows Synchronized: %d%n", tableName, successfulIds.size());
                }
            } catch (Exception e) {
                System.err.printf("Error syncing table %s: %s%n", tableName, e.getMessage());
            }
        }
    }
}
