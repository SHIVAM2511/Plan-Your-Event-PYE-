package com.example.pye.EventBus;

import com.example.pye.Model.ServiceProvidermodel;

public class ServiceProviderItemClick {
    private boolean success;
    private ServiceProvidermodel serviceProvidermodel;

    public ServiceProviderItemClick(boolean success, ServiceProvidermodel serviceProvidermodel) {
        this.success = success;
        this.serviceProvidermodel = serviceProvidermodel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ServiceProvidermodel getServiceProvidermodel() {
        return serviceProvidermodel;
    }

    public void setServiceProvidermodel(ServiceProvidermodel serviceProvidermodel) {
        this.serviceProvidermodel = serviceProvidermodel;
    }
}
