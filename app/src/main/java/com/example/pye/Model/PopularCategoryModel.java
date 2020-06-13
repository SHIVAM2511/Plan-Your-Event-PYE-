package com.example.pye.Model;

public class PopularCategoryModel {
    String service_id,serviceProvider_id;
    String name;
    String imageLink;

    public PopularCategoryModel() {

    }

    public PopularCategoryModel(String name, String imageLink,String service_id,String serviceProvider_id) {
        this.name = name;
        this.imageLink = imageLink;
        this.service_id=service_id;
        this.serviceProvider_id=serviceProvider_id;
    }

    public String getServiceProvider_id() {
        return serviceProvider_id;
    }

    public void setServiceProvider_id(String serviceProvider_id) {
        this.serviceProvider_id = serviceProvider_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
