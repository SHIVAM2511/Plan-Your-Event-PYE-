package com.example.pye.EventBus;

import com.example.pye.Model.servicesModel;

public class ServiceClick {
    private boolean success;
    private servicesModel serviceModel;

    public ServiceClick(boolean success, servicesModel serviceModel) {
        this.success = success;
        this.serviceModel = serviceModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public servicesModel getServiceModel() {
        return serviceModel;
    }

    public void setServiceModel(servicesModel serviceModel) {
        this.serviceModel = serviceModel;
    }
}
