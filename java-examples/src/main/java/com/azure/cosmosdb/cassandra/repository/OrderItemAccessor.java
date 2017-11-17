package com.azure.cosmosdb.cassandra.repository;

import com.azure.cosmosdb.cassandra.OrderItem;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface OrderItemAccessor {

    //Sync calls
    @Query("SELECT * FROM javatest.order_item")
    Result<OrderItem> getAll();

    @Query("SELECT * FROM javatest.order_item where id = ? and shop = ?")
    Result<OrderItem> getPartitionData(Integer id, String shop);

    @Query("SELECT * FROM javatest.order_item where id = ? and shop = ? and item = ?")
    Result<OrderItem> getOrderItemByPK(Integer id, String shop, String item);

    @Query("SELECT * FROM javatest.order_item where id = ? and shop = ? and quantity >= ? order by item")
    Result<OrderItem> getDataByOrderCount(Integer id, String shop, Integer count);

    @Query("SELECT * FROM javatest.order_item WHERE customer_name = ?0 ALLOW FILTERING")
    Result<OrderItem> findByCustomerName(String name);

    @Query("SELECT * FROM javatest.order_item WHERE customer_name = ? LIMIT ? ALLOW FILTERING")
    Result<OrderItem> findByCustomerNameWithLimit(String name, Integer limit);

    @Query("delete FROM javatest.order_item where id = ? and shop = ?")
    ResultSet deletePartition(Integer id, String shop);

    // Async Calls
    @Query("SELECT * FROM javatest.order_item ALLOW FILTERING")
    ListenableFuture<Result<OrderItem>> getAllAsync();

    @Query("SELECT * FROM javatest.order_item where id = ? and shop = ? order by item")
    ListenableFuture<Result<OrderItem>> getPartitionDataAsync(Integer id, String shop);

    @Query("SELECT * FROM javatest.order_item where id = ? and shop = ? and quantity = ?")
    ListenableFuture<Result<OrderItem>> getDataByOrderCountAsync(Integer id, String shop, Integer count);

    @Query("SELECT * FROM javatest.order_item WHERE mobile = ?0 ALLOW FILTERING")
    ListenableFuture<Result<OrderItem>> findByCustomerMobileAsync(String mobile);
}
