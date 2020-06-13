package com.example.pye.DisplayPage.ui.view_orders;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import com.example.pye.Adapter.MyOrderAdapter;
import com.example.pye.Callback.ILoadOrderCallbackListener;
import com.example.pye.Common.Common;
import com.example.pye.Model.UserOrderInformation;
import com.example.pye.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderFragment extends Fragment implements ILoadOrderCallbackListener {
    Unbinder unbinder;
    AlertDialog dialog;
    @BindView(R.id.recycler_orders)
    RecyclerView recycler_orders;

    private ViewOrdersViewModel viewOrdersViewModel;

    private ILoadOrderCallbackListener listener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewOrdersViewModel =
                ViewModelProviders.of(this).get(ViewOrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_view_order, container, false);
        unbinder= ButterKnife.bind(this,root);
        initViews(root);
        loadOrdersFromFirebase();

        viewOrdersViewModel.getMutableLiveDataOrderList().observe(this,orderInformationList -> {
            MyOrderAdapter adapter=new MyOrderAdapter(getContext(),orderInformationList);
            recycler_orders.setAdapter(adapter);
        });

        return root;
    }

    private void loadOrdersFromFirebase() {
        List<UserOrderInformation> orderInformationList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCES)
                .child(Common.currentUser.getUid())
                .child("BookedServices")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot orderSnapshot:dataSnapshot.getChildren())
                        {
                            UserOrderInformation orderInformation=orderSnapshot.getValue(UserOrderInformation.class);
                            orderInformationList.add(orderInformation);
                        }
                        listener.onLoadOrderSuccess(orderInformationList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onLoadOrderFailed(databaseError.getMessage());
                    }
                });
    }

    private void initViews(View root) {
        listener=this;
        dialog= new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
        recycler_orders.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recycler_orders.setLayoutManager(layoutManager);
        recycler_orders.addItemDecoration(new DividerItemDecoration(getContext(),layoutManager.getOrientation()));
    }

    @Override
    public void onLoadOrderSuccess(List<UserOrderInformation> orderInformationList) {
        dialog.dismiss();
        viewOrdersViewModel.setMutableLiveDataOrderList(orderInformationList);

    }

    @Override
    public void onLoadOrderFailed(String message) {
        dialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}