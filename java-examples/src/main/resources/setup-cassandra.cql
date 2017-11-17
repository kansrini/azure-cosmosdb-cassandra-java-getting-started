CREATE KEYSPACE javatest WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 };

USE javatest;

CREATE TABLE IF NOT EXISTS cache_data (
	key text,
	value text,	
	PRIMARY KEY(key)
) WITH default_time_to_live = 20;


CREATE TABLE IF NOT EXISTS order_item (
    id int,
    shop text,
    item text,
    coupon_used boolean,
    customer_name text,
    mobile text,
    price double,
    quantity int,
    shop_ip inet,
    transaction_id uuid,
    PRIMARY KEY ((id, shop), item)
) WITH CLUSTERING ORDER BY (item ASC) ;
