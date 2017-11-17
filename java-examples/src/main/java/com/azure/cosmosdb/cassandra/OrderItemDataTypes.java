package com.azure.cosmosdb.cassandra;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.UUID;

@Table(keyspace = "javatest", name = "order_item_dt",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
public class OrderItemDataTypes {
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

    @Column(name = "price")
    Double price;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "uuid")
    UUID uuid;

    @Column(name = "blobdt")
    ByteBuffer blobdt;

    @Column(name = "decimaldt")
    BigDecimal decimaldt;

    @Column(name = "floatdt")
    Float floatdt;

    @Column(name = "inetdt")
    InetAddress inetdt;

    @Column(name = "shortdt")
    Short shortdt;

    @Column(name = "bigintdt")
    BigInteger bigintdt;

    @Column(name = "bytedt")
    Byte bytedt;

    public OrderItemDataTypes() {

    }

    public OrderItemDataTypes(Integer id, String shop, String item, Boolean coupon_used, Double price,
                              Integer quantity, UUID uuid, ByteBuffer blobdt, /*BigDecimal decimaldt,*/
                              Float floatdt, InetAddress inetdt, Short shortdt, BigInteger bigintdt,
                              Byte bytedt) {
        this.id = id;
        this.shop = shop;
        this.item = item;
        this.coupon_used = coupon_used;
        this.price = price;
        this.quantity = quantity;
        this.uuid = uuid;
        this.blobdt = blobdt;
        //this.decimaldt = decimaldt;
        this.floatdt = floatdt;
        this.inetdt = inetdt;
        this.shortdt = shortdt;
        this.bigintdt = bigintdt;
        this.bytedt = bytedt;
    }

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ByteBuffer getBlobdt() {
        return blobdt;
    }

    public void setBlobdt(ByteBuffer blobdt) {
        this.blobdt = blobdt;
    }

    public BigDecimal getDecimaldt() {
        return decimaldt;
    }

    public void setDecimaldt(BigDecimal decimaldt) {
        this.decimaldt = decimaldt;
    }

    public Float getFloatdt() {
        return floatdt;
    }

    public void setFloatdt(Float floatdt) {
        this.floatdt = floatdt;
    }

    public InetAddress getInetdt() {
        return inetdt;
    }

    public void setInetdt(InetAddress inetdt) {
        this.inetdt = inetdt;
    }

    public Short getShortdt() {
        return shortdt;
    }

    public void setShortdt(Short shortdt) {
        this.shortdt = shortdt;
    }

    public BigInteger getBigintdt() {
        return bigintdt;
    }

    public void setBigintdt(BigInteger bigintdt) {
        this.bigintdt = bigintdt;
    }

    public Byte getBytedt() {
        return bytedt;
    }

    public void setBytedt(Byte bytedt) {
        this.bytedt = bytedt;
    }

    @Override
    public String toString() {
        return "OrderItemDataTypes{" +
                "id=" + id +
                ", shop='" + shop + '\'' +
                ", item='" + item + '\'' +
                ", coupon_used=" + coupon_used +
                ", price=" + price +
                ", quantity=" + quantity +
                ", uuid=" + uuid +
                ", blobdt=" + blobdt +
                ", decimaldt=" + decimaldt +
                ", floatdt=" + floatdt +
                ", inetdt=" + inetdt +
                ", shortdt=" + shortdt +
                ", bigintdt=" + bigintdt +
                ", bytedt=" + bytedt +
                '}';
    }
}
