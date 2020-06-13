package com.example.pye.DisplayPage.ui.Cart;

import android.content.Context;

import com.example.pye.Common.Common;
import com.example.pye.Database.CartDataSource;
import com.example.pye.Database.CartDatabase;
import com.example.pye.Database.CartItem;
import com.example.pye.Database.LocalCartDataSource;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CartDataSource cartDataSource;

    private MutableLiveData<List<CartItem>> mutableLiveDataCartItems;

    public CartViewModel() {
        compositeDisposable=new CompositeDisposable();
    }

    public void initCartDataSource(Context context)
    {
        cartDataSource=new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());

    }

    public void onStop()
    {
        compositeDisposable.clear();
    }


    public MutableLiveData<List<CartItem>> getMutableLiveDataCartItems() {
        if(mutableLiveDataCartItems==null)
            mutableLiveDataCartItems=new MutableLiveData<>();
            getAllCartItems();
        return mutableLiveDataCartItems;
    }

    private void getAllCartItems() {
        compositeDisposable.add(cartDataSource.getAllCart(Common.currentUser.getUid())
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItems -> {
                    mutableLiveDataCartItems.setValue(cartItems);
                }, throwable -> {
                        mutableLiveDataCartItems.setValue(null);
                }));
    }
}