package model;

/**
 * Created by JAY JADIA.
 */

public class OrderModel
{
    int orderId,orderUserId,cost;
    String productIdArray;
    String productRateArray;
    String productQuantArray;
    String status;

    public String getProductNameArray() {
        return productNameArray;
    }

    public void setProductNameArray(String productNameArray) {
        this.productNameArray = productNameArray;
    }

    String productNameArray;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    String orderNumber;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductIdArray() {
        return productIdArray;
    }

    public void setProductIdArray(String productIdArray) {
        this.productIdArray = productIdArray;
    }

    public String getProductRateArray() {
        return productRateArray;
    }

    public void setProductRateArray(String productRateArray) {
        this.productRateArray = productRateArray;
    }

    public String getProductQuantArray() {
        return productQuantArray;
    }

    public void setProductQuantArray(String productQuantArray) {
        this.productQuantArray = productQuantArray;
    }
}
