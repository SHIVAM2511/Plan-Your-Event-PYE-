package com.example.pye.Activity;

import android.view.View;

import com.example.pye.R;
import com.example.pye.UserRegistration;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUp> obj=new ActivityTestRule<SignUp>(SignUp.class);
    private SignUp mactivity=null;

    @Before
    public void setUp() throws Exception {
        mactivity=obj.getActivity();
    }
    @Test
    public void testLaunch()
    {
        View v1,v2,v3,v4,v5,v6,v7,v8;
        v1=mactivity.findViewById(R.id.icon);
        v2=mactivity.findViewById(R.id.edt_userphone);
        v3=mactivity.findViewById(R.id.edt_useremail);
        v4=mactivity.findViewById(R.id.edt_username);
        v5=mactivity.findViewById(R.id.edt_useraddress);
        v6=mactivity.findViewById(R.id.edt_userpassword);
        v7=mactivity.findViewById(R.id.edt_userpasswordcon);
        v8=mactivity.findViewById(R.id.btn_sign_up);


        assertNotNull(v1);
        assertNotNull(v2);
        assertNotNull(v3);
        assertNotNull(v4);
        assertNotNull(v5);
        assertNotNull(v6);
        assertNotNull(v7);
        assertNotNull(v8);
    }
    @After
    public void tearDown() throws Exception {
        mactivity=null;
    }

}