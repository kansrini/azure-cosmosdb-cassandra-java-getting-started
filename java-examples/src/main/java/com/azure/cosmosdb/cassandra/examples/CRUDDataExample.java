package com.azure.cosmosdb.cassandra.examples;

import com.azure.cosmosdb.cassandra.OrderItem;
import com.azure.cosmosdb.cassandra.util.CassandraUtils;
import com.azure.cosmosdb.cassandra.repository.OrderItemAccessor;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import static com.azure.cosmosdb.cassandra.util.Constants.CREATE_ORDERITEM_TABLE;
import static com.azure.cosmosdb.cassandra.util.Constants.DROP_ORDERITEM_TABLE;

/**
 * This class provides examples of
 * - Insert rows to Cassandra Table
 * - Delete rows (based on primary key, partition key )
 * - Select row based on primary key
 * - Select rows based on partition keys
 * - Select rows based on non-partition keys
 * - Apply limit on the number of rows to be fetched
 * - Applying Order by while selecting rows
 */
public class CRUDDataExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(CRUDDataExample.class);
    private static Mapper<OrderItem> orderEntityMapper;

    public static void main(String[] s) throws Exception {

        CassandraUtils utils = new CassandraUtils();
        Session cassandraSession = utils.getSession();

        try {
            // Initialize table
            cassandraSession.execute(DROP_ORDERITEM_TABLE);
            cassandraSession.execute(CREATE_ORDERITEM_TABLE);

            MappingManager manager = new MappingManager(cassandraSession);
            orderEntityMapper = manager.mapper(OrderItem.class);
            OrderItemAccessor orderItemAccessor = manager.createAccessor(OrderItemAccessor.class);

            //insert sample data
            LOGGER.info("Inserting data to order_item.");
            insertSampleData();

            // Get a row by primary key
            LOGGER.info("Fetch a row by primary key");
            OrderItem orderItem = getOrderItemByPK(2, "London-1", "Apple");
            assert (orderItem.getCustomer_name().equals("Bunny"));

            //Update the record
            LOGGER.info("Update values in a row ");
            String updatedItem = "Apple - 1 KG";
            saveOrderItem(new OrderItem(2, "London-1", updatedItem, false, "Bunny",
                    "562321344", 3.0, 2, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
            assert (orderItem.getItem().equals(updatedItem));

            //Delete a row by primary key
            LOGGER.info("Delete a row by primary key");
            deleteOrderItemByPK(99, "Syndney-6", "Candy Pack-50");
            assert (getOrderItemByPK(99, "Syndney-6", "Candy Pack-50") == null);

            //Get rows from a partition
            LOGGER.info("Fetch all rows from a partition");
            Result<OrderItem> partitionData = orderItemAccessor.getPartitionData(2, "London-1");
            assert (partitionData.all().size() == 3);

            //where clause with > operator & order by
            LOGGER.info("Fetch limited rows from a partition");
            List<OrderItem> dataByQuantity = orderItemAccessor.getDataByOrderCount(2, "London-1", 2).all();
            assert (dataByQuantity.size() == 2);

            //Select rows by non-partition key
            LOGGER.info("Fetch rows by non-partition key");
            assert (orderItemAccessor.findByCustomerName("Bunny").all().size() == 3);

            //Select limited rows
            LOGGER.info("Get limited rows across partitions");
            assert (orderItemAccessor.findByCustomerNameWithLimit("Bunny", 2).all().size() == 2);

            // Get all rows across partitions
            LOGGER.info("Get all rows across partitions");
            Result<OrderItem> orderItems = orderItemAccessor.getAll();
            assert (orderItems.all().size() == 4);

            // Delete partitions
            LOGGER.info("Delete all rows in a partition");
            orderItemAccessor.deletePartition(2, "London-1");
            orderItemAccessor.deletePartition(1, "Redmond-3");

        } finally {
            utils.close();
        }
    }

    private static void insertSampleData() throws UnknownHostException {
        saveOrderItem(new OrderItem(1, "Redmond-3", "Razor", false, "Roger Moore",
                "133983432", 2.4, 1, InetAddress.getByName("64.89.12.34"), UUID.randomUUID()));
        saveOrderItem(new OrderItem(2, "London-1", "Apple", false, "Bunny",
                "562321344", 3.0, 2, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
        saveOrderItem(new OrderItem(2, "London-1", "T-Shirt", true, "Bunny",
                "562321344", 2.4, 2, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
        saveOrderItem(new OrderItem(2, "London-1", "Sunglass", true, "Bunny",
                "562321344", 30.8, 1, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
        saveOrderItem(new OrderItem(99, "Syndney-6", "Candy Pack-50", true, "Milan",
                "556723577", 60.8, 1, InetAddress.getByName("32.67.52.124"), UUID.randomUUID()));
    }

    private static void saveOrderItem(OrderItem orderItem) {
        orderEntityMapper.save(orderItem);
    }

    private static OrderItem getOrderItemByPK(Integer id, String shop, String item) {
        return orderEntityMapper.get(id, shop, item);
    }

    private static void deleteOrderItemByPK(Integer id, String shop, String item) {
        orderEntityMapper.delete(id, shop, item);
    }

    private static void deleteOrderItems(Integer id, String shop) {
        orderEntityMapper.delete(id, shop);
    }
}
