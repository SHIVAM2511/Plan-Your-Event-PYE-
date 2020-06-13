package com.example.pye.Activity;

import android.view.View;

import com.example.pye.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class ChooseTimeSlotTest {

    @Rule
    public ActivityTestRule<ChooseTimeSlot> obj=new ActivityTestRule<>(ChooseTimeSlot.class);
    private ChooseTimeSlot mactivity=null;

    @Before
    public void setUp() throws Exception {
        mactivity=obj.getActivity();
    }
    @Test
    public void testLaunch()
    {
        View v1,v2,v3,v4,v5;
        v1=mactivity.findViewById(R.id.txt_choose_time_slot);
        v2=mactivity.findViewById(R.id.calendarView);
        v3=mactivity.findViewById(R.id.recycler_time_slot);
        v4=mactivity.findViewById(R.id.layout_booking_details);
        v5=mactivity.findViewById(R.id.btn_confirm_booking);
        assertNotNull(v1);
        assertNotNull(v2);
        assertNotNull(v3);
        assertNotNull(v4);
        assertNotNull(v5);

    }
    @After
    public void tearDown() throws Exception {
        mactivity=null;
    }

}