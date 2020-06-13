package com.example.pye.DisplayPage.ui.serviceprovidersdetail;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Database;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pye.Activity.ChooseTimeSlot;
import com.example.pye.Common.Common;
import com.example.pye.Database.CartDataSource;
import com.example.pye.Database.CartDatabase;
import com.example.pye.Database.CartItem;
import com.example.pye.Database.LocalCartDataSource;
import com.example.pye.DisplayPage.DisplayActivity;
import com.example.pye.DisplayPage.ui.comments.CommentFragment;
import com.example.pye.EventBus.CounterCartEvent;

import com.example.pye.EventBus.ServiceItemBack;
import com.example.pye.Model.CommentModel;
import com.example.pye.Model.ServiceProvidermodel;
import com.example.pye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvidersDetailFragment extends Fragment {

    private ServiceProvidersDetailViewModel serviceProvidersDetailViewModel;
    private Unbinder unbinder;
    private String number;
    private CartDataSource cartDataSource;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private android.app.AlertDialog waitingDialog;
    private static final int REQUEST_CALL=1;
    LocalBroadcastManager localBroadcastManager;
    private ServiceProvidermodel serviceProvidermodel;
    @BindView(R.id.img_serviceprovider)
    ImageView img_serviceprovider;
    @BindView(R.id.btnCart)
    CounterFab btn_cart;
    @BindView(R.id.btn_rating)
    FloatingActionButton btn_rating;
    @BindView(R.id.serviceprovider_name)
    TextView serviceprovider_name;
    @BindView(R.id.serviceprovider_description)
    TextView serviceprovider_description;
    @BindView(R.id.serviceprovider_price)
    TextView serviceprovider_price;

    @BindView(R.id.service_provider_call)
    TextView service_provider_call;

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.btn_ShowComment)
    Button btnShowComment;

    @BindView(R.id.btn_Book)
    Button btn_book;


    @OnClick(R.id.btn_rating)
     void onRatingButtonClick(){
        showRatingDialog();
    }


    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String uid=firebaseAuth.getCurrentUser().getUid();

    @OnClick(R.id.btnCart)
    void onCartItemAdd(){

        CartItem cartItem=new CartItem();
        cartItem.setUid(uid);
        cartItem.setUserPhone(uid);

        cartItem.setServiceproviderId(Common.selectedServiceProvider.getId());
        cartItem.setServiceproviderName(Common.selectedServiceProvider.getName());
        cartItem.setServiceproviderImage(Common.selectedServiceProvider.getImageLink());
        cartItem.setServiceproviderPrice(Double.valueOf(String.valueOf(Common.selectedServiceProvider.getPrice())));

        compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(getContext(), "Add to Cart Successful", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().postSticky(new CounterCartEvent(true));
                },throwable -> {
                    Toast.makeText(getContext(), "[CART ERROR]"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));


    }

    @OnClick(R.id.btn_ShowComment)
    void onShowCommentButtonClick(){
        CommentFragment commentFragment=CommentFragment.getInstance();
        commentFragment.show(getActivity().getSupportFragmentManager(),"CommentFragment");

    }

    @OnClick(R.id.btn_Book)
    void onClickBook()
    {

        if(Common.selectedServiceProvider!=null)
        {
            startActivity(new Intent(getContext(), ChooseTimeSlot.class));
        }
        else
        {
            Toast.makeText(getContext(), "Nothing happened", Toast.LENGTH_SHORT).show();
        }
    }



    private void showRatingDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Rating Provider");
        builder.setMessage("Please fill information");

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_rating, null);

        RatingBar ratingBar=(RatingBar) itemView.findViewById(R.id.rating_bar);
        EditText edt_comment=(EditText)itemView.findViewById(R.id.edt_comment);
        builder.setView(itemView);

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommentModel commentModel=new CommentModel();
                commentModel.setName(Common.currentUser.getName());
                commentModel.setUid(Common.currentUser.getUid());
                commentModel.setComment(edt_comment.getText().toString());
                commentModel.setRatingValue(ratingBar.getRating());
                Map<String,Object> serverTimeStamp=new HashMap<>();
                serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
                commentModel.setCommentTimeStamp(serverTimeStamp);

                serviceProvidersDetailViewModel.setCommentModel(commentModel);
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @OnClick(R.id.service_provider_call)
    void callServiceProvider(){

        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else
        {
            String dial="tel:"+number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                callServiceProvider();
            }else
            {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        serviceProvidersDetailViewModel =
                ViewModelProviders.of(this).get(ServiceProvidersDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_serviceproviderdetails, container, false);
        unbinder= ButterKnife.bind(this,root);
        initViews();
        serviceProvidersDetailViewModel.getMutableLiveDataServiceProvider().observe(this, serviceProvidermodel -> displayInfo(serviceProvidermodel));
        serviceProvidersDetailViewModel.getMutableLiveDataComment().observe(this,commentModel -> {
            submitRatingToFirebase(commentModel);
        });
        return root;
    }

    private void initViews() {

        cartDataSource=new LocalCartDataSource(CartDatabase.getInstance(getContext()).cartDAO());
        waitingDialog=new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
    }

    private void submitRatingToFirebase(CommentModel commentModel) {
        waitingDialog.show();
        FirebaseDatabase.getInstance().
                getReference(Common.COMMENT_REF)
                .child(Common.selectedServiceProvider.getId())
                .push()
                .setValue(commentModel)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        addRatingToServiceProvider(commentModel.getRatingValue());
                    }
                    waitingDialog.dismiss();
                });
    }

    private void addRatingToServiceProvider(float ratingValue) {
        FirebaseDatabase.getInstance()
                .getReference(Common.SERVICE_REF)
                .child(Common.serviceSelected.getService_id().toString())
                .child("serviceProviderList")
                .child(Common.selectedServiceProvider.getKey().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                                ServiceProvidermodel serviceProvidermodel=dataSnapshot.getValue(ServiceProvidermodel.class);
                                serviceProvidermodel.setKey(Long.valueOf(dataSnapshot.getKey()));
                                if(serviceProvidermodel.getRatingValue()==null)
                                    serviceProvidermodel.setRatingValue(0d);
                                if(serviceProvidermodel.getRatingCount()==null)
                                    serviceProvidermodel.setRatingCount(0l);


                                double sumRating=serviceProvidermodel.getRatingValue()+ratingValue;
                                long ratingCount=serviceProvidermodel.getRatingCount()+1;


                               Map<String,Object> updateData=new HashMap<>();
                               updateData.put("ratingValue",sumRating);
                               updateData.put("ratingCount",ratingCount);

                               serviceProvidermodel.setRatingValue(sumRating);
                               serviceProvidermodel.setRatingCount(ratingCount);


                               dataSnapshot.getRef()
                                       .updateChildren(updateData)
                                       .addOnCompleteListener(task -> {
                                           waitingDialog.dismiss();
                                           if(task.isSuccessful())
                                           {
                                               Toast.makeText(getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                                               Common.selectedServiceProvider=serviceProvidermodel;
                                               serviceProvidersDetailViewModel.setServiceProviderModel(serviceProvidermodel);
                                           }
                                       });
                        }else
                        {
                            waitingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        waitingDialog.dismiss();
                        Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayInfo(ServiceProvidermodel serviceProvidermodel) {
        Glide.with(getContext()).load(serviceProvidermodel.getImageLink()).into(img_serviceprovider);
        serviceprovider_name.setText(new StringBuilder(serviceProvidermodel.getName()));
        serviceprovider_description.setText(new StringBuilder(serviceProvidermodel.getDescription()));
        serviceprovider_price.setText(new StringBuilder(serviceProvidermodel.getPrice().toString()));
        service_provider_call.setText(new StringBuilder(serviceProvidermodel.getPhone()));
        number=serviceProvidermodel.getPhone();

        if(serviceProvidermodel.getRatingValue()!=null)
            ratingBar.setRating(serviceProvidermodel.getRatingValue().floatValue()/ serviceProvidermodel.getRatingCount());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Common.selectedServiceProvider.getName());

    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().postSticky(new ServiceItemBack());
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();

    }

}
