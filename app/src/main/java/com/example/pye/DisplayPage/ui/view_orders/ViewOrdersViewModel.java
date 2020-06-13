package com.example.pye.DisplayPage.ui.view_orders;

import com.example.pye.Model.UserOrderInformation;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewOrdersViewModel extends ViewModel {

    private MutableLiveData<List<UserOrderInformation>> mutableLiveDataOrderList;

    public ViewOrdersViewModel() {
        mutableLiveDataOrderList=new MutableLiveData<>();
    }

    public MutableLiveData<List<UserOrderInformation>> getMutableLiveDataOrderList() {
        return mutableLiveDataOrderList;
    }

    public void setMutableLiveDataOrderList(List<UserOrderInformation> orderList) {
        this.mutableLiveDataOrderList.setValue(orderList);
    }
}