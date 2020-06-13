package com.example.pye.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pye.Callback.IRecyclerClickListener;
import com.example.pye.Common.Common;
import com.example.pye.Database.CartDataSource;
import com.example.pye.Database.CartDatabase;
import com.example.pye.Database.CartItem;
import com.example.pye.Database.LocalCartDataSource;
import com.example.pye.EventBus.CounterCartEvent;
import com.example.pye.EventBus.ServiceProviderItemClick;
import com.example.pye.Model.ServiceProvidermodel;
import com.example.pye.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MyServiceProviderListAdapter extends RecyclerView.Adapter<MyServiceProviderListAdapter.MyViewHolder> {
    private Context context;
    private List<ServiceProvidermodel> serviceProvidermodelList;
    private CompositeDisposable compositeDisposable;
    private CartDataSource cartDataSource;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String uid=firebaseAuth.getCurrentUser().getUid();

    public MyServiceProviderListAdapter(Context context, List<ServiceProvidermodel> serviceProvidermodelList) {
        this.context = context;
        this.serviceProvidermodelList = serviceProvidermodelList;
        this.compositeDisposable=new CompositeDisposable();
        this.cartDataSource=new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_service_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(serviceProvidermodelList.get(position).getImageLink()).into(holder.serviceprovider_image);
        holder.serviceprovider_price.setText(new StringBuilder("Rs.").append(serviceProvidermodelList.get(position).getPrice()));
        holder.serviceprovider_name.setText(new StringBuilder("").append(serviceProvidermodelList.get(position).getName()));


        //Event
        holder.setListener(new IRecyclerClickListener() {
            @Override
            public void onItemClickListener(View view, int pos) {
                Common.selectedServiceProvider=serviceProvidermodelList.get(pos);
                Common.selectedServiceProvider.setKey(Long.valueOf(pos));
                EventBus.getDefault().postSticky(new ServiceProviderItemClick(true,serviceProvidermodelList.get(pos)));
            }
        });




        holder.serviceprovider_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem cartItem=new CartItem();
                cartItem.setUid(uid);
                cartItem.setUserPhone(Common.currentUser.getPhone());

                cartItem.setServiceproviderId(serviceProvidermodelList.get(position).getId());
                cartItem.setServiceproviderName(serviceProvidermodelList.get(position).getName());
                cartItem.setServiceproviderImage(serviceProvidermodelList.get(position).getImageLink());
                cartItem.setServiceproviderPrice(Double.valueOf(String.valueOf(serviceProvidermodelList.get(position).getPrice())));
                cartItem.setUrl(serviceProvidermodelList.get(position).getUrl());
                compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(context, "Add to Cart Successful", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().postSticky(new CounterCartEvent(true));
                },throwable -> {
                    Toast.makeText(context, "[CART ERROR]"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceProvidermodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Unbinder unbinder;
        @BindView(R.id.text_serviceprovider_name)
        TextView serviceprovider_name;
        @BindView(R.id.text_serviceprovider_price)
        TextView serviceprovider_price;
        @BindView(R.id.image_serviceprovider_image)
        ImageView serviceprovider_image;
        @BindView(R.id.image_favorite_provider)
        ImageView serviceprovider_fav;
        @BindView(R.id.image_quick_cart)
        ImageView serviceprovider_cart;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;

        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v,getAdapterPosition());
        }
    }
}
