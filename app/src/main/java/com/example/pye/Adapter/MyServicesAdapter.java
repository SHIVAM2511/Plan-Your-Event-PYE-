package com.example.pye.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pye.Callback.IRecyclerClickListener;
import com.example.pye.Common.Common;
import com.example.pye.EventBus.ServiceClick;
import com.example.pye.Model.servicesModel;
import com.example.pye.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.MyViewHolder> {
    Context context;
    List<servicesModel> servicesModelList;


    public MyServicesAdapter(Context context, List<servicesModel> servicesModelList) {
        this.context = context;
        this.servicesModelList = servicesModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_services_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(servicesModelList.get(position).getImageLink()).into(holder.service_image);
        holder.service_name.setText(new StringBuilder(servicesModelList.get(position).getName()));

        //Event
        holder.setListener(new IRecyclerClickListener() {
            @Override
            public void onItemClickListener(View view, int pos) {
                Common.serviceSelected=servicesModelList.get(pos);
                Common.serviceSelected.setKey(String.valueOf(pos));
                EventBus.getDefault().postSticky(new ServiceClick(true,servicesModelList.get(pos)));
            }
        });
    }

    @Override
        public int getItemCount () {
            return servicesModelList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            Unbinder unbinder;
            @BindView(R.id.image_services)
            ImageView service_image;
            @BindView(R.id.text_services)
            TextView service_name;

            IRecyclerClickListener listener;





            public void setListener(IRecyclerClickListener listener) {
                this.listener = listener;
            }

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                unbinder = ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                listener.onItemClickListener(v, getAdapterPosition());
            }
        }

        @Override
        public int getItemViewType ( int position){
            if (servicesModelList.size() == 1)
                return Common.DEFAULT_COLUMN_COUNT;
            else {
                if (servicesModelList.size() % 2 == 0)
                    return Common.DEFAULT_COLUMN_COUNT;
                else
                    return (position > 1 && position == servicesModelList.size() - 1) ? Common.FULL_WIDTH_COLUMN : Common.DEFAULT_COLUMN_COUNT;
            }
        }
    }
