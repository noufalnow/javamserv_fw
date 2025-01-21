package com.cboard.ast.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TableMetadataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Fetch table names and their primary key columns.
     *
     * @return a list of maps with table name and primary key column.
     */
    public List<Map<String, Object>> fetchTableMetadata() {
        String query = """
            SELECT 
                kcu.table_name,
                kcu.column_name AS primary_key
            FROM 
                information_schema.table_constraints tc
            JOIN 
                information_schema.key_column_usage kcu
            ON 
                tc.constraint_name = kcu.constraint_name
            AND 
                tc.table_name = kcu.table_name
            WHERE 
                tc.constraint_type = 'PRIMARY KEY'
                AND tc.table_schema = 'public';
        """;

        return jdbcTemplate.queryForList(query);
    }
}
