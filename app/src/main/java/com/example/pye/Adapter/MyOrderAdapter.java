package com.example.pye.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pye.Model.UserOrderInformation;
import com.example.pye.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    private Context context;
    private List<UserOrderInformation> orderInformationList;

    public MyOrderAdapter(Context context, List<UserOrderInformation> orderInformationList) {
        this.context = context;
        this.orderInformationList = orderInformationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(orderInformationList.get(position).getServiceProviderImage()).into(holder.booked_service_provider_img);
        holder.booked_txt_service_provider_name.setText(orderInformationList.get(position).getServiceProviderName());
        holder.booked_txt_service_provider_price.setText(orderInformationList.get(position).getServicePrice());
        holder.booked_service_provider_date.setText(orderInformationList.get(position).getOrderTime());
    }

    @Override
    public int getItemCount() {
        return orderInformationList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.booked_service_provider_img)
        ImageView booked_service_provider_img;
        @BindView(R.id.booked_txt_service_provider_name)
        TextView booked_txt_service_provider_name;
        @BindView(R.id.booked_txt_service_provider_price)
        TextView booked_txt_service_provider_price;
        @BindView(R.id.booked_service_provider_date)
        TextView booked_service_provider_date;

        Unbinder unbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
        }
    }
}
