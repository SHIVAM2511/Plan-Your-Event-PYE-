package com.example.pye.DisplayPage.ui.servicelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.example.pye.Adapter.MyServiceProviderListAdapter;
import com.example.pye.Common.Common;
import com.example.pye.EventBus.ServiceItemBack;
import com.example.pye.Model.ServiceProvidermodel;
import com.example.pye.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ServiceListFragment extends Fragment {

    private ServiceListViewModel serviceListViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_service_list)
    RecyclerView recycler_serviceprovider_list;

    LayoutAnimationController layoutAnimationController;
    MyServiceProviderListAdapter myServiceProviderListAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        serviceListViewModel =
                ViewModelProviders.of(this).get(ServiceListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_service_list, container, false);
        unbinder= ButterKnife.bind(this,root);
        initViews();
        serviceListViewModel.getMutableLiveDataServiceProviderList().observe(this, new Observer<List<ServiceProvidermodel>>() {
            @Override
            public void onChanged(List<ServiceProvidermodel> serviceProvidermodels) {
                myServiceProviderListAdapter =new MyServiceProviderListAdapter(getContext(),serviceProvidermodels);
                recycler_serviceprovider_list.setAdapter(myServiceProviderListAdapter);
                recycler_serviceprovider_list.setLayoutAnimation(layoutAnimationController);
            }
        });
        return root;
    }

    private void initViews() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Common.serviceSelected.getName());
        recycler_serviceprovider_list.setHasFixedSize(true);
        recycler_serviceprovider_list.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().postSticky(new ServiceItemBack());
        super.onDestroy();
    }
}