package com.cboard.ast.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JdbcSyncService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Fetch records with is_synched set to '0' from the specified table.
     *
     * @param tableName  the name of the table to query.
     * @param primaryKey the name of the primary key column.
     * @return a list of records as a map of column names to values.
     */
    public List<Map<String, Object>> fetchUnsyncedRecords(String tableName, String primaryKey) {
        // Use '0' for is_synched instead of false
        String query = String.format("SELECT %s, * FROM %s WHERE is_synched = '0'", primaryKey, tableName);
        
        
        //List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        //rows.forEach(row -> System.out.println("Row: " + row));

        
        return jdbcTemplate.queryForList(query);
    }

    /**
     * Update is_synched to '1' for the specified IDs in the given table.
     *
     * @param tableName  the name of the table to update.
     * @param primaryKey the name of the primary key column.
     * @param ids        the list of IDs for which the is_synched should be updated.
     */
    public void updateSyncFlag(String tableName, String primaryKey, List<Long> ids) {
        if (ids.isEmpty()) {
            return; // Nothing to update
        }

        // Dynamically construct the query string with tableName and primaryKey
        String query = String.format("UPDATE %s SET is_synched = '1' WHERE %s IN (:ids)", tableName, primaryKey);

        // Prepare named parameters
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);

        // Execute the query
        try {
            int rowsUpdated = namedParameterJdbcTemplate.update(query, params);
            System.out.printf("Updated %d rows in table %s%n", rowsUpdated, tableName);
        } catch (Exception e) {
            System.err.printf("Error updating sync flag in table %s: %s%n", tableName, e.getMessage());
            throw e;
        }
    }

}
