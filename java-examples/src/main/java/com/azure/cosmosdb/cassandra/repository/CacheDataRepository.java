package com.azure.cosmosdb.cassandra.repository;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

import com.azure.cosmosdb.cassandra.CacheData;

/**
 * This test class gives examples of create, delete table
 * Insert & delete data from the table
 */
public class CacheDataRepository {
    private static final String SCHEMA_NAME = "javatest";
    private static final String TABLE_NAME = "cache_data";
    private Session session;
    private PreparedStatement pstmt;
    public CacheDataRepository(Session session) {
        this.session = session;
    }

    /**
     * Creates the javatest table.
     */
    public void createKeyspace(Session cassandraSession) {
        final String query = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append(SCHEMA_NAME)
                .append(" WITH replication = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 } ").toString();
        cassandraSession.execute(query);
    }

    /**
     * Creates the cache_data table.
     */
    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(SCHEMA_NAME).append(".").
                append(TABLE_NAME).append("(").append("key text PRIMARY KEY, ").
                append("value text) WITH default_time_to_live = 20;");

        final String query = sb.toString();
        session.execute(query);
    }

    /**
     * Insert a row on to cache_data table
     * @param key
     * @param value
     */
    public void insertCacheData(String key, String value) {
        if(pstmt == null )
            pstmt = prepareInsertCacheData();
        BoundStatement boundStatement = new BoundStatement(pstmt);
        session.execute(boundStatement.bind(key, value));
    }

    /**
     * Select all cache from cache_data
     *
     * @return
     */
    public List<CacheData> selectAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(SCHEMA_NAME).append(".").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<CacheData> cache = new ArrayList<CacheData>();

        for (Row r : rs) {
            CacheData cacheData = new CacheData(r.getString("key"), r.getString("value"));
            cache.add(cacheData);
        }
        return cache;
    }

    /**
     * Delete cache_data table.
     *
     */
    public void deleteTable() {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(SCHEMA_NAME).append(".").append(TABLE_NAME);

        final String query = sb.toString();
        session.execute(query);
    }

    private PreparedStatement prepareInsertCacheData() {
        StringBuilder insertStatement = new StringBuilder("INSERT INTO ").append(SCHEMA_NAME).append(".").append(TABLE_NAME).
                append("(key, value) ").append("VALUES (?, ?);");

        return session.prepare(insertStatement.toString());
    }

}