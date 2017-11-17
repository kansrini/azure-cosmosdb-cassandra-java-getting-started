package com.azure.cosmosdb.cassandra;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.net.InetAddress;
import java.util.UUID;

@Table(keyspace = "javatest", name = "order_item",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
public class OrderItem {

    @PartitionKey(0)
    @Column(name = "id")
    Integer id;

    @PartitionKey(1)
    @Column(name = "shop")
    String shop;

    @ClusteringColumn
    @Column(name = "item")
    String item;

    @Column(name = "coupon_used")
    Boolean coupon_used;

    @Column(name = "customer_name")
    String customer_name;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "price")
    Double price;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "shop_ip")
    InetAddress shop_ip;

    @Column(name = "transaction_id")
    UUID transaction_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getCoupon_used() {
        return coupon_used;
    }

    public void setCoupon_used(Boolean coupon_used) {
        this.coupon_used = coupon_used;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public InetAddress getShop_ip() {
        return shop_ip;
    }

    public void setShop_ip(InetAddress shop_ip) {
        this.shop_ip = shop_ip;
    }

    public UUID getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(UUID transaction_id) {
        this.transaction_id = transaction_id;
    }

    public OrderItem() {

    }

    public OrderItem(Integer id, String shop, String item, Boolean coupon_used, String customer_name, String mobile,
                     Double price, Integer quantity, InetAddress shop_ip, UUID transaction_id) {
        this.id = id;
        this.shop = shop;
        this.item = item;
        this.coupon_used = coupon_used;
        this.customer_name = customer_name;
        this.mobile = mobile;
        this.price = price;
        this.quantity = quantity;
        this.shop_ip = shop_ip;
        this.transaction_id = transaction_id;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", shop='" + shop + '\'' +
                ", item='" + item + '\'' +
                ", coupon_used=" + coupon_used +
                ", customer_name='" + customer_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", shop_ip=" + shop_ip.getHostAddress() +
                ", transaction_id=" + transaction_id.toString() +
                '}';
    }
}
