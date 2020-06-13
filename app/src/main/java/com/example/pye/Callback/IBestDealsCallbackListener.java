package com.example.pye.Callback;

import com.example.pye.Model.BestDealModel;

import java.util.List;

public interface IBestDealsCallbackListener {
    void onBestDealsLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}
