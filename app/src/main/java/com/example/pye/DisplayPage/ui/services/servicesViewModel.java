package com.example.pye.DisplayPage.ui.services;

import com.example.pye.Callback.IServicesCallbackListener;
import com.example.pye.Common.Common;
import com.example.pye.Model.servicesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class servicesViewModel extends ViewModel implements IServicesCallbackListener {
    private MutableLiveData<List<servicesModel>>  serviceListMutable;
    private MutableLiveData<String> messageError=new MutableLiveData<>();
    private IServicesCallbackListener servicesCallbackListener;

    public servicesViewModel() {
        servicesCallbackListener=this;
    }

    public MutableLiveData<List<servicesModel>> getServiceListMutable() {
        if(serviceListMutable==null)
        {
            serviceListMutable=new MutableLiveData<>();
            messageError =new MutableLiveData<>();
            loadServices();
        }
        return serviceListMutable;
    }

    private void loadServices() {
        List<servicesModel> tempList=new ArrayList<>();
        DatabaseReference serviceRef= FirebaseDatabase.getInstance().getReference(Common.SERVICE_REF);
        serviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapshot:dataSnapshot.getChildren())
                {
                    servicesModel serModel=itemSnapshot.getValue(servicesModel.class);
                    serModel.setService_id(itemSnapshot.getKey());
                    tempList.add(serModel);
                }
                servicesCallbackListener.onServicesLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                servicesCallbackListener.onServicesLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onServicesLoadSuccess(List<servicesModel> servicesModelList) {
        serviceListMutable.setValue(servicesModelList);
    }

    @Override
    public void onServicesLoadFailed(String message) {
        messageError.setValue(message);
    }
}