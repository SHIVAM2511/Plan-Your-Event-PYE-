package com.example.pye.DisplayPage.ui.servicelist;

import com.example.pye.Common.Common;
import com.example.pye.Model.ServiceProvidermodel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class  ServiceListViewModel extends ViewModel {

    private MutableLiveData<List<ServiceProvidermodel>> mutableLiveDataServiceProviderList;

    public ServiceListViewModel() {

    }

    public MutableLiveData<List<ServiceProvidermodel>> getMutableLiveDataServiceProviderList() {
        if(mutableLiveDataServiceProviderList==null)
            mutableLiveDataServiceProviderList=new MutableLiveData<>();
        mutableLiveDataServiceProviderList.setValue(Common.serviceSelected.getServiceProviderList());

        return mutableLiveDataServiceProviderList;
    }
}