package com.azure.cosmosdb.cassandra.examples;

import java.io.IOException;

import com.azure.cosmosdb.cassandra.repository.UserRepository;
import com.azure.cosmosdb.cassandra.util.CassandraUtils;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example class which will demonstrate following operations on Cassandra Database on CosmosDB
 * - Create Keyspace
 * - Create Table
 * - Insert Rows
 * - Select all data from a table
 * - Select a row from a table
 */
public class UserProfile {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfile.class);

    public static void main(String[] s) throws Exception {

        CassandraUtils utils = new CassandraUtils();
        Session cassandraSession = utils.getSession();

        try {
            UserRepository repository = new UserRepository(cassandraSession);

            //Create keyspace in cassandra database
            repository.createKeyspace();
            
            //Create table in cassandra database
            repository.createTable();

            //Insert rows into user table
            PreparedStatement preparedStatement = repository.prepareInsertStatement();
            repository.insertUser(preparedStatement, 1, "Kanna", "Bangalore");
            repository.insertUser(preparedStatement, 2, "Siddesh", "Mumbai");
            repository.insertUser(preparedStatement, 3, "Govind", "Belgum");
            repository.insertUser(preparedStatement, 4, "Mohit", "Gurgaon");
            repository.insertUser(preparedStatement, 5, "Vinod", "Dubai");

            LOGGER.info("Select all users");
            repository.selectAllUsers();

            LOGGER.info("Select a user by id (3)");
            repository.selectUser(3);

            repository.deleteTable();

        } finally {
            utils.close();
        }
    }
}