package com.example.pye.DisplayPage.ui.home;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.pye.Adapter.MyBestDealAdapter;
import com.example.pye.Adapter.MyPopularAdapter;
import com.example.pye.Model.BestDealModel;
import com.example.pye.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment  {
    private HomeViewModel homeViewModel;
    Unbinder unbinder;

    @BindView(R.id.recyclerMP)
    RecyclerView recyclerPopular;
    @BindView(R.id.viewpager)
    LoopingViewPager viewPager;
    LayoutAnimationController layoutAnimationController;


    public HomeFragment() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater layoutInflater,ViewGroup container,Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get( HomeViewModel.class);
        View root=layoutInflater.inflate(R.layout.fragment_home,container,false);
        unbinder= ButterKnife.bind(this,root);
        init();
        homeViewModel.getPopularList().observe(this, PopularCategoryModel->{

            MyPopularAdapter adapter=new MyPopularAdapter(getContext(),PopularCategoryModel);
            recyclerPopular.setAdapter(adapter);
            recyclerPopular.setLayoutAnimation(layoutAnimationController);

        });
        homeViewModel.getBestDealList().observe(this,BestDealModel -> {
            MyBestDealAdapter adapter=new MyBestDealAdapter(getContext(), BestDealModel,true);
            viewPager.setAdapter(adapter);
        });
        return root;
    }

    private void init() {
        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        recyclerPopular.setHasFixedSize(true);
        recyclerPopular.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.resumeAutoScroll();
    }

    @Override
    public void onPause() {
        viewPager.pauseAutoScroll();
        super.onPause();
    }
}
