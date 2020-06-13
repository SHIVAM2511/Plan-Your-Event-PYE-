package com.example.pye.Callback;

import com.example.pye.Model.PopularCategoryModel;
import com.example.pye.Model.servicesModel;

import java.util.List;

public interface IServicesCallbackListener {
    void onServicesLoadSuccess(List<servicesModel> servicesModelList);
    void onServicesLoadFailed(String message);

}
