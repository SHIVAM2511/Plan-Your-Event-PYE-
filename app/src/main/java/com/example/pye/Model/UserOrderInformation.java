package com.example.pye.Model;

public class UserOrderInformation {
    private String serviceProviderName,serviceProviderImage,orderTime,servicePrice;

    public UserOrderInformation() {
    }

    public UserOrderInformation(String serviceProviderName, String serviceProviderImage, String orderTime,String servicePrice) {
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderImage = serviceProviderImage;
        this.orderTime = orderTime;
        this.servicePrice=servicePrice;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProviderImage() {
        return serviceProviderImage;
    }

    public void setServiceProviderImage(String serviceProviderImage) {
        this.serviceProviderImage = serviceProviderImage;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
