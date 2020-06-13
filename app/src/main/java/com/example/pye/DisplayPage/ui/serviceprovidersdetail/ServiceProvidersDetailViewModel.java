package com.example.pye.DisplayPage.ui.serviceprovidersdetail;

import com.example.pye.Common.Common;
import com.example.pye.Model.CommentModel;
import com.example.pye.Model.ServiceProvidermodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServiceProvidersDetailViewModel extends ViewModel {

    private MutableLiveData<ServiceProvidermodel>  mutableLiveDataServiceProvider;
    private MutableLiveData<CommentModel> mutableLiveDataComment;

    public void setCommentModel(CommentModel commentModel)
    {
        if(mutableLiveDataComment!=null)
        {
            mutableLiveDataComment.setValue(commentModel);
        }
    }

    public MutableLiveData<CommentModel> getMutableLiveDataComment() {
        return mutableLiveDataComment;
    }

    public ServiceProvidersDetailViewModel() {
        mutableLiveDataComment=new MutableLiveData<>();

    }

    public MutableLiveData<ServiceProvidermodel> getMutableLiveDataServiceProvider() {
        if(mutableLiveDataServiceProvider==null)
            mutableLiveDataServiceProvider=new MutableLiveData<>();
        mutableLiveDataServiceProvider.setValue(Common.selectedServiceProvider);
        return mutableLiveDataServiceProvider;
    }

    public void setServiceProviderModel(ServiceProvidermodel serviceProvidermodel) {
        if(mutableLiveDataServiceProvider!=null) {
            mutableLiveDataServiceProvider.setValue(serviceProvidermodel);
        }
    }
}