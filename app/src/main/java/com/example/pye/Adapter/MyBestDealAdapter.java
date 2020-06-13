package com.example.pye.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.bumptech.glide.Glide;
import com.example.pye.EventBus.BestDealItemClick;
import com.example.pye.Model.BestDealModel;
import com.example.pye.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBestDealAdapter extends LoopingPagerAdapter<BestDealModel> {
    @BindView(R.id.bestdeal_image)
    ImageView bestdeal_image;
    @BindView(R.id.text_best_deal)
    TextView text_best_deal;
    Unbinder unbinder;
    public MyBestDealAdapter(Context context, List<BestDealModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.layout_best_deals,container,false);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        unbinder= ButterKnife.bind(this,convertView);
        Glide.with(convertView).load(itemList.get(listPosition).getImageLink()).placeholder(R.drawable.googleg_standard_color_18).into(bestdeal_image);
        text_best_deal.setText(itemList.get(listPosition).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new BestDealItemClick(itemList.get(listPosition)));
            }
        });
    }
}
