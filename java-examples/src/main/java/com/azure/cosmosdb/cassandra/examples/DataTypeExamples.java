package com.azure.cosmosdb.cassandra.examples;

import com.azure.cosmosdb.cassandra.OrderItem;
import com.azure.cosmosdb.cassandra.OrderItemDataTypes;
import com.azure.cosmosdb.cassandra.util.CassandraUtils;
import com.azure.cosmosdb.cassandra.repository.OrderItemDTAccessor;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class DataTypeExamples {
    private static Mapper<OrderItemDataTypes> orderEntityMapper;
    private static Session cassandraSession = null;
    public static void main(String[] s) throws Exception {

        CassandraUtils utils = new CassandraUtils();
        cassandraSession = utils.getSession();

        try {
            MappingManager manager = new MappingManager(cassandraSession);

            orderEntityMapper = manager.mapper(OrderItemDataTypes.class);
            OrderItemDTAccessor orderItemAccessor = manager.createAccessor(OrderItemDTAccessor.class);

            //insert sample data
            insertSampleData();

            // Get a row by primary key
            OrderItemDataTypes orderItem = orderItemAccessor.getOrderItemByPK(3, "Redmond-3", "Razor").one();
            assert(orderItem.getBigintdt().compareTo(new BigInteger("11239944565467567856756759534534534534344")) ==0 );
            insertData();
            //Update the record
           /* String updatedItem = "Apple - 1 KG";
            saveOrderItem(new OrderItemDataTypes(2, "London-1", updatedItem, false, "Bunny", "562321344", 3.0, 2));
            assert (orderItem.getItem().equals(updatedItem));

            //Delete a row by primary key
            deleteOrderItemByPK(99, "Syndney-6", "Candy Pack-50");
            assert (getOrderItemByPK(99, "Syndney-6", "Candy Pack-50") == null);

            //Get rows from a partition
            Result<OrderItem> partitionData = orderItemAccessor.getPartitionData(2,"London-1");
            assert(partitionData.all().size() == 3);

            //where clause with > operator & order by
            List<OrderItem> dataByQuantity =  orderItemAccessor.getDataByOrderCount(2, "London-1", 1).all();
            assert (dataByQuantity.size() ==2);

            //Select rows by non-partition key
            assert (orderItemAccessor.findByCustomerName("Bunny").all().size() == 3);

            //Select limited rows
            assert (orderItemAccessor.findByCustomerNameWithLimit("Bunny", 2).all().size() == 2);

            // Get all rows across partitions
            Result<OrderItem> orderItems = orderItemAccessor.getAll();
            assert(orderItems.all().size() == 4);

            // Delete partitions
            orderItemAccessor.deletePartition(2, "London-1");
            orderItemAccessor.deletePartition(1, "Redmond-3");*/

        } finally {
            utils.close();
        }
    }

    private static void insertSampleData() throws UnknownHostException {

       /* OrderItemDataTypes(Integer id, String shop, String item, Boolean coupon_used, Double price,
                Integer quantity, UUID uuid, ByteBuffer blobdt, BigDecimal decimaldt,
                Float floatdt, InetAddress inetdt, Short shortdt, BigInteger bigintdt,
                Byte bytedt)*/
        ByteBuffer bb = ByteBuffer.wrap("Test 123".getBytes());
        InetAddress ip=InetAddress.getByName("www.google.com");
        System.out.println("IP Address: " + ip.toString());

        saveOrderItem(new OrderItemDataTypes(3, "Redmond-3", "Razor", false, new Double("1"), 7,  UUID.randomUUID(),bb,
                /*new BigDecimal("10"),*/ 23.6f, ip, new Short("10"), new BigInteger("11239944565467567856756759534534534534344"), (byte)3));
    }

    private static void saveOrderItem(OrderItemDataTypes orderItem) {
        orderEntityMapper.save(orderItem);
    }

    private static OrderItemDataTypes getOrderItemByPK(Integer id, String shop, String item) {
        return orderEntityMapper.get(id, shop, item);
    }

    private static void deleteOrderItemByPK(Integer id, String shop, String item) {
        orderEntityMapper.delete(id, shop, item);
    }

    private static void deleteOrderItems(Integer id, String shop) {
        orderEntityMapper.delete(id, shop);
    }

    private static PreparedStatement prepareInsertData() {
        StringBuilder insertStatement = new StringBuilder("insert into javatest.order_item_dt (id, shop, item, bigintdt) values (?, ?, ?, ?)");

        return cassandraSession.prepare(insertStatement.toString());
    }

    public static void insertData() {
        PreparedStatement statement = prepareInsertData();
        BoundStatement boundStatement = new BoundStatement(statement);
        cassandraSession.execute(boundStatement.bind(9, "Bangalore-1", "Fan", new BigInteger("1234")));
    }
}
