package com.example.pye.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pye.Activity.WebViewCart;
import com.example.pye.Database.CartItem;
import com.example.pye.DisplayPage.ui.serviceprovidersdetail.ServiceProvidersDetailViewModel;
import com.example.pye.Model.ServiceProvidermodel;
import com.example.pye.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    Context context;
    List<CartItem> cartItemList;
    ServiceProvidermodel serviceProvidermodel;
    ServiceProvidersDetailViewModel serviceProvidersDetailViewModel;

    public MyCartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(cartItemList.get(position).getServiceproviderImage())
        .into(holder.img_cart);
        holder.txt_service_provider_name.setText(new StringBuilder(cartItemList.get(position).getServiceproviderName()));
        holder.txt_service_provider_price.setText(new StringBuilder(""+cartItemList.get(position).getServiceproviderPrice()));
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Have a view on this "+ new StringBuilder(cartItemList.get(position).getServiceproviderName()));
                intent.setType("text/plain");
                context.startActivity(Intent.createChooser(intent,"Send To"));
            }
        });

        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(cartItemList.get(position).getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public CartItem getItemAtPosition(int pos) {
        return cartItemList.get(pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Unbinder unbinder;
        @BindView(R.id.img_cart)
        ImageView img_cart;
        @BindView(R.id.txt_service_provider_name)
        TextView txt_service_provider_name;
        @BindView(R.id.txt_service_provider_price)
        TextView txt_service_provider_price;

        @BindView(R.id.btn_share)
        Button btn_share;

        @BindView(R.id.btn_book)
        Button btn_book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
        }
    }
}
