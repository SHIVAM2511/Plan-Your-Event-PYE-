package com.example.pye.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pye.Model.categoryItem;
import com.example.pye.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.Adapter<CategoryViewHolder.MyViewHolder> {

    Context context;
    List<categoryItem> categoryItems;

    public CategoryViewHolder(List<categoryItem> ci)
    {
        categoryItems=ci;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item,parent,false);
            return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        categoryItem categoryItemss=categoryItems.get(position);
        holder.occname.setText(categoryItemss.getName());
        Picasso.get().load(categoryItemss.getImageLink()).into(holder.occimage);
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView occname;
        ImageView occimage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            occname=(TextView)itemView.findViewById(R.id.occasion_name);
            occimage=(ImageView) itemView.findViewById(R.id.occasion_image);

        }
    }
}
