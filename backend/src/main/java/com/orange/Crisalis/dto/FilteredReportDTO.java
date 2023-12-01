package com.orange.Crisalis.dto;

import com.orange.Crisalis.enums.OrderState;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FilteredReportDTO {
    private int clientID;
    private int OrderID;
    private String clientName;
    private String sellableGood;
    private int quantity;
    private Double price;
    private Double discount;
    private Double subtotal;
    private Double total;
    private Date orderDate;
    private Double taxes;
    private OrderState orderStatus;
    private Double warrantyValue;
    private Double supportCharge;

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

    public String getSellableGood() {
        return sellableGood;
    }

    public void setSellableGood(String sellableGoodName) {
        this.sellableGood = sellableGoodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }
    public Double getTaxes(){
        return taxes;
    }

    public void setOrderStatus(OrderState orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderState getOrderStatus() {
        return orderStatus;
    }

    public void setSupportCharge(Double supportCharge) {
        this.supportCharge = supportCharge;
    }

    public Double getSupportCharge() {
        return supportCharge;
    }

    public void setWarrantyValue(Double warrantyValue) {
        this.warrantyValue = warrantyValue;
    }

    public Double getWarrantyValue() {
        return warrantyValue;
    }
}
