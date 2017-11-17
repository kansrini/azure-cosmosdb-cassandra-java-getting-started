package com.azure.cosmosdb.cassandra.examples;

import com.azure.cosmosdb.cassandra.OrderItem;
import com.azure.cosmosdb.cassandra.repository.OrderItemAccessor;
import com.azure.cosmosdb.cassandra.util.CassandraUtils;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.azure.cosmosdb.cassandra.util.Constants.CREATE_ORDERITEM_TABLE;
import static com.azure.cosmosdb.cassandra.util.Constants.DROP_ORDERITEM_TABLE;


/**
 * This class provides samples to read data in pages using datastax java driver for cassandra
 */

public class PagingOperationsExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(PagingOperationsExample.class);
    private static Mapper<OrderItem> orderEntityMapper;
    private final static int RESULTS_PER_PAGE =5;

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

            orderEntityMapper = manager.mapper(OrderItem.class);
            prepareData();

            //Set paging size
            utils.getCluster().getConfiguration().getQueryOptions().setFetchSize(RESULTS_PER_PAGE);

            //examples to validate getting results in page based on the FetchSize
            getResultsInPage(cassandraSession, 30, "Bangalore-9");

            cleanData(orderItemAccessor);
        } finally {
            utils.close();
        }
    }

    /**
     * examples to validate getting results in page based on the FetchSize
     * @param session
     * @param id
     * @param shop
     */
    private static void getResultsInPage(Session session, Integer id, String shop) {
        PreparedStatement statement = session.prepare("SELECT * FROM javatest.order_item WHERE id = ? and shop= ? ALLOW FILTERING");
        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet rs = session.execute(boundStatement.bind(id, shop));

        PagingState pagingState = rs.getExecutionInfo().getPagingState();

        int availableRows = rs.getAvailableWithoutFetching();
        assert (availableRows == RESULTS_PER_PAGE);

        for (Row row : rs) {
            LOGGER.info(String.format("Id: %s, Shop: %s, Customer: %s, Item: %s, Quantity: %s, Shop IP: %s, Tx Id: %s",
                    row.getInt("id"), row.getString("shop"), row.getString("customer_name"),
                    row.getString("item"), row.getInt("quantity"), row.getInet("shop_ip").getHostAddress(),
                    row.getUUID("transaction_id").toString()));

            //This will let to read the rows based on fetch size.
            if (--availableRows == 0) {
                break;
            }
        }

        if (pagingState != null) {
            LOGGER.info("After fetching page 1, pagingState not Null, hence more detata availables");

            //Can fetch remaining rows
            for (Row row : rs) {
                LOGGER.info(String.format("Id: %s, Shop: %s, Customer: %s, Item: %s, Quantity: %s, Shop IP: %s, Tx Id: %s",
                        row.getInt("id"), row.getString("shop"), row.getString("customer_name"),
                        row.getString("item"), row.getInt("quantity"), row.getInet("shop_ip").getHostAddress(),
                        row.getUUID("transaction_id").toString()));
            }
        } else {
            //pagingState is null because there are no more rows available.
           // This is an erroneous state to be in
            assert (false);
        }
    }

    /**
     * Insert random partition data
     */
    private static void prepareData() throws UnknownHostException {
        insertRandomOrderItems(99,"Delhi-3", 5);
        insertRandomOrderItems(30, "Bangalore-9", 12);
        insertRandomOrderItems(100, "Seattle-1", 4);
        insertRandomOrderItems(4563, "Sydney-8", 3);
    }

    /**
     * Cleanup all the data inserted
     * @param orderItemAccessor
     */
    private static  void cleanData(OrderItemAccessor orderItemAccessor) {
        orderItemAccessor.deletePartition(99,"Delhi-3");
        orderItemAccessor.deletePartition(30, "Bangalore-9");
        orderItemAccessor.deletePartition(100, "Seattle-1");
        orderItemAccessor.deletePartition(4563, "Sydney-8");
    }

    /**
     * Insert randmon order_item rows for a given id & shop
     * @param id
     * @param shop
     * @param rows
     */
    private static void insertRandomOrderItems(Integer id, String shop, int rows) throws UnknownHostException {

        Random rand = new Random();

        for(int i =0; i< rows; i++) {
            OrderItem orderItem = new OrderItem(id, shop, UUID.randomUUID().toString(), rand.nextBoolean(),
                    ("Customer" + rand.nextInt(5)), String.valueOf(rand.nextLong()), rand.nextDouble(),
                    rand.nextInt(10), InetAddress.getLocalHost(), UUID.randomUUID());
            orderEntityMapper.save(orderItem);
        }
    }
}
