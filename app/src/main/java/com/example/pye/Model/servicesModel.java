package com.example.pye.Model;

import java.util.List;

public class servicesModel {
    private String service_id;
    private String name,imageLink;
    List<ServiceProvidermodel> serviceProviderList;

    public servicesModel() {
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

    public List<ServiceProvidermodel> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProvidermodel> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }

    public void setKey(String valueOf) {
    }
}
