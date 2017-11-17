package com.azure.cosmosdb.cassandra.examples;

import com.azure.cosmosdb.cassandra.OrderItem;
import com.azure.cosmosdb.cassandra.repository.OrderItemAccessor;
import com.azure.cosmosdb.cassandra.util.CassandraUtils;
import static com.azure.cosmosdb.cassandra.util.Constants.*;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * This class provides samples to do CRUD operations asynchronously using datastax java driver for Cassandra
 */
public class CRUDDataAsyncExamples {
    private static final Logger LOGGER = LoggerFactory.getLogger(CRUDDataAsyncExamples.class);
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

            //insert sample data asynchronously
            LOGGER.info("Inserting data to order_item asynchronously.");
            insertSampleOrderItemAsync();

            // Get a row by primary key asynchronously
            LOGGER.info("Fetch a row by primary key");
            ListenableFuture<OrderItem> orderItem = getOrderItemByPKAsync(99, "Syndney-6", "Candy Pack-50");
            assert (orderItem.get().getCustomer_name().equals("Peter Orsos"));

            //Delete a row by primary key asynchronously
            LOGGER.info("Delete a row by primary key");
            deleteOrderItemByPKAsync(99, "Syndney-6", "Candy Pack-50");
            assert (getOrderItemByPKAsync(99, "Syndney-6", "Candy Pack-50").get() == null);

            //Get rows from a partition asynchronously
            LOGGER.info("Fetch all rows from a partition");
            ListenableFuture<Result<OrderItem>> result = orderItemAccessor.getPartitionDataAsync(2, "London-1");
            assert (result.get().all().size() == 3);

            //Async where clause with > operator
            LOGGER.info("Fetch limited rows from a partition");
            ListenableFuture<Result<OrderItem>> dataByQuantity = orderItemAccessor.getDataByOrderCountAsync(2, "London-1", 2);
            assert (dataByQuantity.get().all().size() == 2);

            //Select rows by non-partition key
            LOGGER.info("Fetch rows by non-partition key");
            assert (orderItemAccessor.findByCustomerMobileAsync("562321344").get().all().size() == 3);

            // Get all rows across partitions
            LOGGER.info("Get all rows across partitions");
            ListenableFuture<Result<OrderItem>> orderItems = orderItemAccessor.getAllAsync();
            assert (orderItems.get().all().size() == 4);

        } finally {
            utils.close();
        }
    }

    private static void insertSampleOrderItemAsync() throws UnknownHostException {
        insertOrderItemAsync(new OrderItem(1, "Redmond-3", "Razor", false,
                "Ella Hayward", "133983432", 2.4, 1, InetAddress.getByName("64.89.12.34"), UUID.randomUUID()));
        insertOrderItemAsync(new OrderItem(2, "London-1", "Apple", false,
                "Morgan Potter", "562321344", 3.0, 2, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
        insertOrderItemAsync(new OrderItem(2, "London-1", "T-Shirt", true,
                "Morgan Potter", "562321344", 2.4, 2, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
        insertOrderItemAsync(new OrderItem(2, "London-1", "Sunglass", true,
                "Morgan Potter", "562321344", 30.8, 1, InetAddress.getByName("121.112.64.99"), UUID.randomUUID()));
        insertOrderItemAsync(new OrderItem(99, "Syndney-6", "Candy Pack-50", true,
                "Peter Orsos", "556723577", 60.8, 1, InetAddress.getByName("32.67.52.124"), UUID.randomUUID()));
    }

    private static void insertOrderItemAsync(OrderItem orderItem) {
        ListenableFuture<Void> saveFuture = orderEntityMapper.saveAsync(orderItem);
    }

    private static ListenableFuture<OrderItem> getOrderItemByPKAsync(Integer id, String shop, String item) {
        return orderEntityMapper.getAsync(id, shop, item);
    }

    private static ListenableFuture<Void> deleteOrderItemByPKAsync(Integer id, String shop, String item) {
        return orderEntityMapper.deleteAsync(id, shop, item);
    }
}
