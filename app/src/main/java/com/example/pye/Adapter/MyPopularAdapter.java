package com.example.pye.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pye.Callback.IRecyclerClickListener;
import com.example.pye.EventBus.PopularCategoryClick;
import com.example.pye.Model.PopularCategoryModel;
import com.example.pye.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPopularAdapter extends RecyclerView.Adapter<MyPopularAdapter.MyViewHolder> {

    Context context;
    List<PopularCategoryModel> popularCategoryModelList;

    public MyPopularAdapter(Context context, List<PopularCategoryModel> popularCategoryModelList) {
        this.context = context;
        this.popularCategoryModelList = popularCategoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_most_popular,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(popularCategoryModelList.get(i).getImageLink()).placeholder(R.drawable.ic_menu_gallery).into(myViewHolder.category_image);
        myViewHolder.text_category_name.setText(popularCategoryModelList.get(i).getName());

        myViewHolder.setListener((view, pos) -> EventBus.getDefault().postSticky(new PopularCategoryClick(popularCategoryModelList.get(pos))));
    }

    @Override
    public int getItemCount() {
        return popularCategoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;
        @BindView(R.id.text_category_names)
        TextView text_category_name;
        @BindView(R.id.category_image)
        CircleImageView category_image;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder=ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v,getAdapterPosition());
        }
    }
}
