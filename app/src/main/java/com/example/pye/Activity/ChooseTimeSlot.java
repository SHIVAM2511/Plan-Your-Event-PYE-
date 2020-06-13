package com.example.pye.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pye.Adapter.MyTimeSlotAdapter;
import com.example.pye.Callback.ITimeSlotLoadListener;
import com.example.pye.Common.Common;
import com.example.pye.Common.SpacesItemDecoration;
import com.example.pye.Model.BookingInformation;
import com.example.pye.Model.TimeSlot;
import com.example.pye.Model.UserOrderInformation;
import com.example.pye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChooseTimeSlot extends AppCompatActivity implements ITimeSlotLoadListener {

    DatabaseReference serviceProviderRef;
    LocalBroadcastManager localBroadcastManager;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    AlertDialog dialog;
    HorizontalCalendar horizontalCalendar;
    RecyclerView recycler_time_slot;
    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat1;
    TextView service_provider_name,booking_time,service_provider_number,service_provider_price;
    Button btn_booking;

    BroadcastReceiver confirmBookingReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Common.currentTimeSlot=intent.getIntExtra(Common.KEY_TIME_SLOT,-1);
            if(intent.getStringExtra("NOTIFY").equals("AVAILABLE"))
            {
                btn_booking.setVisibility(View.VISIBLE);
            }else
            {
                btn_booking.setVisibility(View.INVISIBLE);
            }
            setData();
        }
    };

    private void setData() {
        service_provider_name.setText(Common.selectedServiceProvider.getName());
        service_provider_price.setText(Common.selectedServiceProvider.getPrice().toString());
        service_provider_number.setText(Common.selectedServiceProvider.getPhone());
        booking_time.setText(new StringBuilder(Common.setTimeSlotToString(Common.currentTimeSlot-1))
        .append(" on ")
        .append(simpleDateFormat1.format(Common.currentDate.getTime())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_slot);
        service_provider_name=findViewById(R.id.txt_service_provider_name);
        service_provider_number=findViewById(R.id.txt_service_provider_phone);
        booking_time=findViewById(R.id.txt_booking_time);
        service_provider_price=findViewById(R.id.txt_service_provider_price);
        btn_booking=findViewById(R.id.btn_confirm_booking);

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingInformation bookingInformation=new BookingInformation();
                bookingInformation.setServiceId(Common.serviceSelected.getService_id());
                bookingInformation.setServiceProviderId(Common.selectedServiceProvider.getKey());
                bookingInformation.setServiceProviderName(Common.selectedServiceProvider.getName());
                bookingInformation.setCustomerName(Common.currentUser.getName());
                bookingInformation.setCustomerPhone(Common.currentUser.getPhone());
                bookingInformation.setTime(new StringBuilder(Common.setTimeSlotToString(Common.currentTimeSlot-1))
                        .append(" on ")
                        .append(simpleDateFormat1.format(Common.currentDate.getTime())).toString());
                bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

                Toast.makeText(ChooseTimeSlot.this, bookingInformation.getCustomerName(), Toast.LENGTH_SHORT).show();

                UserOrderInformation orderInformation=new UserOrderInformation();
                orderInformation.setServiceProviderName(Common.selectedServiceProvider.getName());
                orderInformation.setServiceProviderImage(Common.selectedServiceProvider.getImageLink());
                orderInformation.setOrderTime(new StringBuilder(Common.setTimeSlotToString(Common.currentTimeSlot-1))
                        .append(" on ")
                        .append(simpleDateFormat1.format(Common.currentDate.getTime())).toString());
                orderInformation.setServicePrice(Common.selectedServiceProvider.getPrice().toString());

                //

                DatabaseReference bookedServices=FirebaseDatabase.getInstance()
                        .getReference(Common.USER_REFERENCES)
                        .child(Common.currentUser.getUid())
                        .child("BookedServices")
                        .child(Common.simpleDateFormat.format(Common.currentDate.getTime()));
                DatabaseReference bookingDate=FirebaseDatabase.getInstance()
                        .getReference(Common.SERVICE_REF)
                        .child(Common.serviceSelected.getService_id().toString())
                        .child("serviceProviderList")
                        .child(Common.selectedServiceProvider.getKey().toString())
                        .child(Common.simpleDateFormat.format(Common.currentDate.getTime()))
                        .child(String.valueOf(Common.currentTimeSlot));

                bookingDate.setValue(bookingInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ChooseTimeSlot.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(ChooseTimeSlot.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                bookedServices.setValue(orderInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                        }
                        else {
                            Toast.makeText(ChooseTimeSlot.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });




        recycler_time_slot=findViewById(R.id.recycler_time_slot);
        calendarView=findViewById(R.id.calendarView);
        iTimeSlotLoadListener=this;
        simpleDateFormat=new SimpleDateFormat("dd_MM_yyyy");
        simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");
        localBroadcastManager=LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver,new IntentFilter(Common.KEY_CONFIRM_BOOKING));
        View view=findViewById(R.id.layout_choose_time_slot);
        dialog= new SpotsDialog.Builder().setContext(ChooseTimeSlot.this).setCancelable(false).build();

        init(view);

    }


    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }

    @Override
    public void onTimeLoadSuccess(List<TimeSlot> timeSlotList) {
        MyTimeSlotAdapter adapter=new MyTimeSlotAdapter(this,timeSlotList);
        recycler_time_slot.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void onTimeLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onTimeLoadEmpty() {
        MyTimeSlotAdapter adapter=new MyTimeSlotAdapter(this);
        recycler_time_slot.setAdapter(adapter);
        dialog.dismiss();
    }


    private void init(View view) {

        recycler_time_slot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recycler_time_slot.setLayoutManager(gridLayoutManager);
        recycler_time_slot.addItemDecoration(new SpacesItemDecoration(8));


        //Calendar
        Calendar startDate=Calendar.getInstance();
        startDate.add(Calendar.DATE,0);
        Calendar endDate=Calendar.getInstance();
        endDate.add(Calendar.DATE,6);

        horizontalCalendar=new HorizontalCalendar.Builder(view,R.id.calendarView)
                .range(startDate,endDate)
                .datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();

        loadAvailableTimeSlotOfServiceProvider(Common.selectedServiceProvider.getKey().toString()
                ,simpleDateFormat.format(startDate.getTime()));

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                if(Common.currentDate.getTimeInMillis()!= date.getTimeInMillis())
                {
                    btn_booking.setVisibility(View.INVISIBLE);
                    Common.currentDate=date;
                    loadAvailableTimeSlotOfServiceProvider(Common.selectedServiceProvider.getKey().toString()
                            ,simpleDateFormat.format(date.getTime()));
                }
            }
        });



    }



    private void loadAvailableTimeSlotOfServiceProvider(String serviceProviderId, String bookdate) {
        dialog.show();

        serviceProviderRef= FirebaseDatabase.getInstance()
                .getReference(Common.SERVICE_REF)
                .child(Common.serviceSelected.getService_id().toString())
                .child("serviceProviderList")
                .child(serviceProviderId);


        serviceProviderRef
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            DatabaseReference date=FirebaseDatabase.getInstance()
                                    .getReference(Common.SERVICE_REF)
                                    .child(Common.serviceSelected.getService_id().toString())
                                    .child("serviceProviderList")
                                    .child(serviceProviderId)
                                    .child(bookdate);


                            date.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.hasChildren())
                                    {
                                        iTimeSlotLoadListener.onTimeLoadEmpty();
                                    }else
                                    {
                                        List<TimeSlot> timeSlots=new ArrayList<>();
                                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                        {

                                            TimeSlot timeSlot=dataSnapshot1.getValue(TimeSlot.class);
                                            timeSlots.add(timeSlot);
                                            iTimeSlotLoadListener.onTimeLoadSuccess(timeSlots);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    iTimeSlotLoadListener.onTimeLoadFailed(databaseError.getMessage());
                                    Toast.makeText(ChooseTimeSlot.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else
                        {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
