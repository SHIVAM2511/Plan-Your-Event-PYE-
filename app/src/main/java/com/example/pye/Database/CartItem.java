package com.example.pye.Database;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="serviceproviderId")
    private String serviceproviderId;

    @ColumnInfo(name="serviceproviderName")
    private String serviceproviderName;

    @ColumnInfo(name="serviceproviderImage")
    private String serviceproviderImage;

    @ColumnInfo(name="serviceproviderPrice")
    private Double serviceproviderPrice;

    @ColumnInfo(name="userPhone")
    private String userPhone;

    @ColumnInfo(name="uid")
    private String uid;

    @ColumnInfo(name="serviceproviderurl")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServiceproviderId() {
        return serviceproviderId;
    }

    public void setServiceproviderId(String serviceproviderId) {
        this.serviceproviderId = serviceproviderId;
    }

    public String getServiceproviderName() {
        return serviceproviderName;
    }

    public void setServiceproviderName(String serviceproviderName) {
        this.serviceproviderName = serviceproviderName;
    }

    public String getServiceproviderImage() {
        return serviceproviderImage;
    }

    public void setServiceproviderImage(String serviceproviderImage) {
        this.serviceproviderImage = serviceproviderImage;
    }

    public Double getServiceproviderPrice() {
        return serviceproviderPrice;
    }

    public void setServiceproviderPrice(Double serviceproviderPrice) {
        this.serviceproviderPrice = serviceproviderPrice;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
