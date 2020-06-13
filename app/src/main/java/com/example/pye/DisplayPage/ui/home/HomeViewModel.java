package com.example.pye.DisplayPage.ui.home;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import com.example.pye.Callback.IBestDealsCallbackListener;
import com.example.pye.Callback.IPopularCallbackListener;
import com.example.pye.Common.Common;
import com.example.pye.Model.BestDealModel;
import com.example.pye.Model.PopularCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IPopularCallbackListener, IBestDealsCallbackListener {
    private MutableLiveData<List<PopularCategoryModel>> popularList;
    private MutableLiveData<List<BestDealModel>> bestDealList;
    private MutableLiveData<String> messageError;
    private IPopularCallbackListener iPopularCallbackListener;
    private IBestDealsCallbackListener iBestDealsCallbackListener;

    public HomeViewModel() {

        iPopularCallbackListener=this;
        iBestDealsCallbackListener=this;
    }

    public MutableLiveData<List<BestDealModel>> getBestDealList() {
        if(bestDealList==null)
        {
            bestDealList=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadBestDealList();
        }
        return bestDealList;
    }

    private void loadBestDealList() {
        List<BestDealModel> tempList=new ArrayList<>();
        DatabaseReference bestDealRef= FirebaseDatabase.getInstance().getReference().child(Common.BEST_DEALS_REF);
        bestDealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapShot: dataSnapshot.getChildren())
                {
                    BestDealModel bestDealModel=itemSnapShot.getValue(BestDealModel.class);
                    tempList.add(bestDealModel);
                }
                iBestDealsCallbackListener.onBestDealsLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    iBestDealsCallbackListener.onBestDealLoadFailed(databaseError.getMessage());
            }
        });

    }

    public MutableLiveData<List<PopularCategoryModel>> getPopularList() {
        if(popularList==null)
        {
            popularList=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadPopularList();
        }
        return popularList;
    }

    private void loadPopularList() {
        List<PopularCategoryModel> tempList=new ArrayList<>();
        DatabaseReference popularRef= FirebaseDatabase.getInstance().getReference().child(Common.POPULAR_CATEGORY_REF);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapShot: dataSnapshot.getChildren())
                {
                    PopularCategoryModel popularCategoryModel=itemSnapShot.getValue(PopularCategoryModel.class);
                    tempList.add(popularCategoryModel);
                }
                iPopularCallbackListener.onPopularLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iPopularCallbackListener.onPopularLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels) {
        popularList.setValue(popularCategoryModels);
    }

    @Override
    public void onPopularLoadFailed(String message) {
        messageError.setValue(message);
    }

    @Override
    public void onBestDealsLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealList.setValue(bestDealModels);
    }

    @Override
    public void onBestDealLoadFailed(String message) {
        messageError.setValue(message);
    }
}
