package com.kiran.major.dto;

public enum OrderStatus {

    IN_PROGRESS(1,"In Progress"),
    ORDER_RECEIVED(2,"Order Received"),

    OUT_FOR_DELIVERY(3,"Out For Delivery"),

    ORDER_DELIVERED(4,"Order Delivered"),

    ORDER_CANCEL(5,"Order Cancelled");

    private Integer id;

    private String name;

    OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
