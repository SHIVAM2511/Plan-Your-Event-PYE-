package com.example.pye.DisplayPage.ui.comments;

import com.example.pye.Model.CommentModel;
import com.example.pye.Model.ServiceProvidermodel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommentViewModel extends ViewModel {
    private MutableLiveData<List<CommentModel>> mutableLiveDataServiceProviderList;

    public CommentViewModel() {
        mutableLiveDataServiceProviderList=new MutableLiveData<>();
    }

    public MutableLiveData<List<CommentModel>> getMutableLiveDataServiceProviderList() {
        return mutableLiveDataServiceProviderList;
    }

    public void setCommentList(List<CommentModel> commentList)
    {
        mutableLiveDataServiceProviderList.setValue(commentList);
    }
}
