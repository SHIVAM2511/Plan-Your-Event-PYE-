package com.example.pye.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pye.Callback.IRecyclerClickListener;
import com.example.pye.Common.Common;
import com.example.pye.Model.TimeSlot;
import com.example.pye.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {

    Context context;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyTimeSlotAdapter(Context context, List<TimeSlot> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.cardViewList=new ArrayList<>();
        this.localBroadcastManager=LocalBroadcastManager.getInstance(context);
    }

    public MyTimeSlotAdapter(Context context) {
        this.context=context;
        this.timeSlotList=new ArrayList<>();
        this.cardViewList=new ArrayList<>();
        this.localBroadcastManager=LocalBroadcastManager.getInstance(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_time_slot,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            holder.text_time_slot.setText(new StringBuilder(Common.setTimeSlotToString(position)).toString());
            if(timeSlotList.size()==0)
            {

                holder.card_time_slot.setCardBackgroundColor(ContextCompat.getColor(context,R.color.available));
                holder.text_time_slot_description.setText("Available");
                holder.text_time_slot_description.setTextColor(ContextCompat.getColor(context,android.R.color.black));
                holder.text_time_slot.setTextColor(ContextCompat.getColor(context,android.R.color.black));


            }else
            {
                for(TimeSlot slotValue: timeSlotList)
                {
                    int slot=Integer.parseInt(slotValue.getSlot().toString());
                    if(slot-1==position)
                    {
                        holder.card_time_slot.setTag(Common.DISABLE_TAG);
                        holder.card_time_slot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.booked));
                        holder.text_time_slot_description.setText("Booked");
                        holder.text_time_slot_description.setTextColor(ContextCompat.getColor(context,android.R.color.black));
                        holder.text_time_slot.setTextColor(ContextCompat.getColor(context,android.R.color.black));

                    }
                }
            }

            if(!cardViewList.contains(holder.card_time_slot))
            {
                cardViewList.add(holder.card_time_slot);
            }

            holder.setiRecyclerClickListener(new IRecyclerClickListener() {
                @Override
                public void onItemClickListener(View view, int pos) {
                    for(CardView cardView:cardViewList)
                    {
                        if(cardView.getTag()==null)
                        {
                            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.available));
                        }
                        if(cardView.getTag()!=null)
                        {
                            cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.booked));
                        }

                    }

                    if(holder.card_time_slot.getTag()==null) {

                        holder.card_time_slot.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light));
                        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
                        intent.putExtra(Common.KEY_TIME_SLOT, position + 1);
                        intent.putExtra("NOTIFY","AVAILABLE");
                        localBroadcastManager.sendBroadcast(intent);

                    }else {
                        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
                        intent.putExtra(Common.KEY_TIME_SLOT, position + 1);
                        intent.putExtra("NOTIFY", holder.card_time_slot.getTag().toString());
                        localBroadcastManager.sendBroadcast(intent);
                    }

                }
            });
    }

    @Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;
        @BindView(R.id.card_time_slot)
        CardView card_time_slot;

        @BindView(R.id.text_time_slot)
        TextView text_time_slot;

        @BindView(R.id.text_time_slot_description)
        TextView text_time_slot_description;


        IRecyclerClickListener iRecyclerClickListener;

        public void setiRecyclerClickListener(IRecyclerClickListener iRecyclerClickListener) {
            this.iRecyclerClickListener = iRecyclerClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerClickListener.onItemClickListener(v,getAdapterPosition());
        }
    }
}
