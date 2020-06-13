package com.example.pye.DisplayPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.andremion.counterfab.CounterFab;
import com.example.pye.Activity.ProfileActivity;
import com.example.pye.Common.Common;
import com.example.pye.Database.CartDataSource;
import com.example.pye.Database.CartDatabase;
import com.example.pye.Database.LocalCartDataSource;
import com.example.pye.EventBus.BestDealItemClick;
import com.example.pye.EventBus.CounterCartEvent;
import com.example.pye.EventBus.HideFABCart;
import com.example.pye.EventBus.PopularCategoryClick;
import com.example.pye.EventBus.ServiceClick;
import com.example.pye.EventBus.ServiceItemBack;
import com.example.pye.EventBus.ServiceProviderItemClick;
import com.example.pye.Model.ServiceProvidermodel;
import com.example.pye.Model.UserModel;
import com.example.pye.Model.servicesModel;
import com.example.pye.R;
import com.example.pye.UserRegistration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavController navController;
    DatabaseReference ref;
    View hView;
    TextView name;
    TextView phone;
    String uid;
    String username;
    String userphone;
    ImageView userProfile;
    private CartDataSource cartDataSource;

    android.app.AlertDialog dialog;

    int serviceClickId=-1;

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    @BindView(R.id.quick_cart)
    CounterFab quick_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        dialog=new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        cartDataSource=new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_services, R.id.nav_service_provider_details,
                R.id.nav_view_orders, R.id.nav_cart, R.id.nav_service_list)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        hView=navigationView.getHeaderView(0);
        name=(TextView)hView.findViewById(R.id.txt_user_name);
        phone=(TextView)hView.findViewById(R.id.text_user_phone);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getCurrentUser().getUid();
        ref= FirebaseDatabase.getInstance().getReference().child(Common.USER_REFERENCES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(DisplayActivity.this, "Inside", Toast.LENGTH_SHORT).show();
                username=dataSnapshot.child(uid).child("name").getValue().toString();
                userphone=dataSnapshot.child(uid).child("phone").getValue().toString();
                name.setText(username);
                phone.setText(userphone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        FloatingActionButton fab = findViewById(R.id.quick_cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_cart);
            }
        });
        countCartItem();


        userProfile=hView.findViewById(R.id.image_user);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DisplayActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawer.closeDrawers();

        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                if(menuItem.getItemId()!=serviceClickId)
                    navController.navigate(R.id.nav_home);
                break;

            case R.id.nav_services:
                if(menuItem.getItemId()!=serviceClickId)
                    navController.navigate(R.id.nav_services);
                break;
            case R.id.nav_cart:
                if(menuItem.getItemId()!=serviceClickId)
                    navController.navigate(R.id.nav_cart);
                break;

            case R.id.nav_view_orders:
                if(menuItem.getItemId()!=serviceClickId)
                    navController.navigate(R.id.nav_view_orders);
                break;
            case R.id.nav_sign_out:
                    signOut();
                break;

        }
        serviceClickId=menuItem.getItemId();
        return true;
    }

    private void signOut() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sign Out")
                .setMessage("Do you really want to sign out?")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Common.selectedServiceProvider=null;
                        Common.serviceSelected=null;
                        Common.currentUser=null;
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(DisplayActivity.this, UserRegistration.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();

    }

    //EventBus

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onServiceSelected(ServiceClick event)
    {
        if(event.isSuccess())
        {
            navController.navigate(R.id.nav_service_list);
            //Toast.makeText(this, "You clicked "+event.getServiceModel().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onServiceProviderItemClick(ServiceProviderItemClick event)
    {
        if(event.isSuccess())
        {
            navController.navigate(R.id.nav_service_provider_details);
            //Toast.makeText(this, "You clicked "+event.getServiceModel().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onHideFABEvent(HideFABCart event)
    {
        if(event.isHidden())
        {
            quick_cart.hide();
        }else
        {
            quick_cart.show();
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onCartCounter(CounterCartEvent event)
    {
        if(event.isSuccess())
        {
            countCartItem();
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onPopularItemClick(PopularCategoryClick event)
    {
        if(event.getPopularCategoryModel()!=null)
        {

            dialog.show();
            FirebaseDatabase.getInstance().getReference("Services")
                    .child(event.getPopularCategoryModel().getService_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                Common.serviceSelected=dataSnapshot.getValue(servicesModel.class);
                                Common.serviceSelected.setService_id(dataSnapshot.getKey());

                                FirebaseDatabase.getInstance()
                                        .getReference("Services")
                                        .child(event.getPopularCategoryModel().getService_id())
                                        .child("serviceProviderList")
                                        .orderByChild("id")
                                        .equalTo(event.getPopularCategoryModel().getServiceProvider_id())
                                        .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.exists())
                                                {
                                                    for(DataSnapshot itemSnapshot:dataSnapshot.getChildren())
                                                    {
                                                        Common.selectedServiceProvider=itemSnapshot.getValue(ServiceProvidermodel.class);
                                                        Common.selectedServiceProvider.setKey(Long.valueOf(itemSnapshot.getKey()));
                                                    }
                                                    NavController navController = Navigation.findNavController(DisplayActivity.this, R.id.nav_host_fragment);
                                                    navController.navigate(R.id.nav_service_provider_details);
                                                }else
                                                {
                                                    Toast.makeText(DisplayActivity.this, "Item doesn't exist", Toast.LENGTH_SHORT).show();
                                                }
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                dialog.dismiss();
                                                Toast.makeText(DisplayActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }else
                            {
                                dialog.dismiss();
                                Toast.makeText(DisplayActivity.this, "Provider not available currently", Toast.LENGTH_SHORT).show();
                            }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            dialog.dismiss();
                            Toast.makeText(DisplayActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    });

        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onBestDealItemClick(BestDealItemClick event)
    {
        if(event.getBestDealModel()!=null)
        {

            dialog.show();
            FirebaseDatabase.getInstance().getReference("Services")
                    .child(event.getBestDealModel().getService_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                Common.serviceSelected=dataSnapshot.getValue(servicesModel.class);
                                Common.serviceSelected.setService_id(dataSnapshot.getKey());
                                Toast.makeText(DisplayActivity.this, Common.serviceSelected.getService_id(), Toast.LENGTH_SHORT).show();

                                FirebaseDatabase.getInstance()
                                        .getReference("Services")
                                        .child(event.getBestDealModel().getService_id())
                                        .child("serviceProviderList")
                                        .orderByChild("id")
                                        .equalTo(event.getBestDealModel().getServiceProvider_id())
                                        .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.exists())
                                                {
                                                    for(DataSnapshot itemSnapshot:dataSnapshot.getChildren())
                                                    {
                                                        Common.selectedServiceProvider=itemSnapshot.getValue(ServiceProvidermodel.class);
                                                    }
                                                    NavController navController = Navigation.findNavController(DisplayActivity.this, R.id.nav_host_fragment);
                                                    navController.navigate(R.id.nav_service_provider_details);
                                                }else
                                                {
                                                    Toast.makeText(DisplayActivity.this, "Item doesn't exist", Toast.LENGTH_SHORT).show();
                                                }
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                dialog.dismiss();
                                                Toast.makeText(DisplayActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }else
                            {
                                dialog.dismiss();
                                Toast.makeText(DisplayActivity.this, "Provider not available currently", Toast.LENGTH_SHORT).show();
                            }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            dialog.dismiss();
                            Toast.makeText(DisplayActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    });

        }
    }

    private void countCartItem() {

        cartDataSource.countItemInCart(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        quick_cart.setCount(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(!e.getMessage().contains("Query returned empty"))
                        {
                            Toast.makeText(DisplayActivity.this, "[COUNT CART]"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }else
                        {
                            quick_cart.setCount(0);
                        }
                    }
                });
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onServiceItemBack(ServiceItemBack event)
    {
        serviceClickId=-1;
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
    }



}

