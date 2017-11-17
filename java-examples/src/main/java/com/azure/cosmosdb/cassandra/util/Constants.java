package com.azure.cosmosdb.cassandra.util;

public final class Constants {

    public static final String CREATE_ORDERITEM_TABLE = "CREATE TABLE IF NOT EXISTS javatest.order_item (id int, shop text, " +
            "item text, coupon_used boolean, customer_name text, mobile text, price double, quantity int, shop_ip inet, " +
            "transaction_id uuid, PRIMARY KEY ((id, shop), item)) WITH CLUSTERING ORDER BY (item ASC)";

    public static final String DROP_ORDERITEM_TABLE = "DROP TABLE IF EXISTS javatest.order_item";
}
