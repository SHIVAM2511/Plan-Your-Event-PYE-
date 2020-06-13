package com.example.pye.Activity;

import android.view.View;

import com.example.pye.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class ProfileActivityTest {
    @Rule
    public ActivityTestRule<ProfileActivity> obj=new ActivityTestRule<ProfileActivity>(ProfileActivity.class);
    private ProfileActivity mactivity=null;

    @Before
    public void setUp() throws Exception {
        mactivity=obj.getActivity();
    }
    @Test
    public void testLaunch()
    {
        View v1,v2,v3,v4,v5,v6,v7;
        v1=mactivity.findViewById(R.id.update_details);
        v2=mactivity.findViewById(R.id.img_userimage);
        v3=mactivity.findViewById(R.id.txt_username);
        v4=mactivity.findViewById(R.id.edt_profile_name);
        v5=mactivity.findViewById(R.id.edt_profile_phone);
        v6=mactivity.findViewById(R.id.edt_profile_email);
        v7=mactivity.findViewById(R.id.edt_profile_address);


        assertNotNull(v1);
        assertNotNull(v2);
        assertNotNull(v3);
        assertNotNull(v4);
        assertNotNull(v5);
        assertNotNull(v6);
        assertNotNull(v7);
    }
    @After
    public void tearDown() throws Exception {
        mactivity=null;
    }

}