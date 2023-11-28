package com.orange.Crisalis.dto;

public class DiscountServiceDTO {
    private int clientID;
    private String clientName;
    private String service;
    private Double discount;
    private int OrderID;

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int id) {
        this.clientID = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getService() {
        return service;
    }

    public void setService(String sellableGoodName) {
        this.service = sellableGoodName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }
}
