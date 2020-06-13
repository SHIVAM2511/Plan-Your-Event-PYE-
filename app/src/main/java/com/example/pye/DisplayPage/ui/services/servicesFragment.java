package com.example.pye.DisplayPage.ui.services;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import com.example.pye.Adapter.MyServicesAdapter;
import com.example.pye.Common.Common;
import com.example.pye.Common.SpacesItemDecoration;
import com.example.pye.EventBus.ServiceItemBack;
import com.example.pye.R;

import org.greenrobot.eventbus.EventBus;

public class servicesFragment extends Fragment {

    private servicesViewModel servicesViewModel;
    MyServicesAdapter myServicesAdapter;

    Unbinder unbinder;
    @BindView(R.id.recycler_services)
    RecyclerView recycler_services;
    AlertDialog dialog;
    LayoutAnimationController layoutAnimationController;
    MyServicesAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        servicesViewModel =
                ViewModelProviders.of(this).get(servicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_services, container, false);

        unbinder= ButterKnife.bind(this,root);
        initViews();
        servicesViewModel.getMessageError().observe(this, s -> {
            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        servicesViewModel.getServiceListMutable().observe(this,servicesModelList -> {
            dialog.dismiss();
            adapter=new MyServicesAdapter(getContext(),servicesModelList);
            recycler_services.setAdapter(adapter);
            recycler_services.setLayoutAnimation(layoutAnimationController);

        });
        return root;
    }

    private void initViews() {
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        dialog.show();
        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        GridLayoutManager layoutManager =new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(adapter!=null)
                {
                    switch (adapter.getItemViewType(position))
                    {
                        case Common.DEFAULT_COLUMN_COUNT: return 1;
                        case  Common.FULL_WIDTH_COLUMN:return 2;
                        default: return -1;
                    }
                }
                return -1;
            }
        });
        recycler_services.setLayoutManager(layoutManager);
        recycler_services.addItemDecoration(new SpacesItemDecoration(8));
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().postSticky(new ServiceItemBack());
        super.onDestroy();
    }
}