package com.azure.cosmosdb.cassandra.repository;

import com.azure.cosmosdb.cassandra.OrderItem;
import com.azure.cosmosdb.cassandra.OrderItemDataTypes;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface OrderItemDTAccessor {

    //Sync calls
    @Query("SELECT * FROM javatest.order_item_dt")
    Result<OrderItemDataTypes> getAll();

    @Query("SELECT * FROM javatest.order_item_dt where id = ? and shop = ?")
    Result<OrderItemDataTypes> getPartitionData(Integer id, String shop);

    @Query("SELECT * FROM javatest.order_item_dt where id = ? and shop = ? and item = ?")
    Result<OrderItemDataTypes> getOrderItemByPK(Integer id, String shop, String item);


}
