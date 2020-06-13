package com.example.pye.Common;

import com.example.pye.Model.ServiceProvidermodel;
import com.example.pye.Model.UserModel;
import com.example.pye.Model.servicesModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {
    public static final String USER_REFERENCES="Users";
    public static final String POPULAR_CATEGORY_REF="MostPopular";
    public static final String BEST_DEALS_REF ="BestDeals" ;
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN =1 ;
    public static final String SERVICE_REF ="Services" ;
    public static final String COMMENT_REF ="Comments";
    public static final int TIME_SLOT_TOTAL =2;
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static  int currentTimeSlot = -1;
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static UserModel currentUser;
    public static servicesModel serviceSelected;
    public static ServiceProvidermodel selectedServiceProvider;
    public static Calendar currentDate=Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd_MM_yyyy");

    public static String setTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0: return "9:00am-5:00pm";

            case 1: return "7:00pm-5:00am";

            default: return "Closed";

        }
    }
}
