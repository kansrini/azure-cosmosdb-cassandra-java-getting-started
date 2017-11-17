package com.azure.cosmosdb.cassandra.examples;

import com.azure.cosmosdb.cassandra.repository.CacheDataRepository;
import com.azure.cosmosdb.cassandra.util.CassandraUtils;
import com.datastax.driver.core.Session;

import java.io.IOException;

/**
 * This class provides an example of validating default_time_to_live properties set on Cassandra Table
 */
public class CacheDataWithTTLExample {

    public static void main(String[] s) throws Exception {

        CassandraUtils utils = new CassandraUtils();
        Session cassandraSession = utils.getSession();

        try {
            CacheDataRepository repository = new CacheDataRepository(cassandraSession);
            repository.createKeyspace(cassandraSession);
            repository.createTable();

            //Insert Data into cache_data table
            repository.insertCacheData("key1", "value1");
            repository.insertCacheData("key2", "value2");

            assert (repository.selectAll().size() == 2);

            //Wait for 20 sec
            Thread.sleep(20000);

            //Ensure data gets deleted after 20 sec
            assert (repository.selectAll().size() == 0);

            repository.deleteTable();

        } finally {
            utils.close();
        }
    }

}
