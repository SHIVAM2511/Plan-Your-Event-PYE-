package com.example.pye.Callback;

import com.example.pye.Model.UserOrderInformation;

import java.util.List;

public interface ILoadOrderCallbackListener {
    void onLoadOrderSuccess(List<UserOrderInformation> orderInformationList);
    void onLoadOrderFailed(String message);
}
