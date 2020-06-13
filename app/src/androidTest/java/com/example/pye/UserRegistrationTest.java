package com.example.pye;

import android.view.View;

import com.example.pye.DisplayPage.DisplayActivity;
import com.example.pye.Model.UserModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class UserRegistrationTest {

    @Rule
    public ActivityTestRule<UserRegistration> obj=new ActivityTestRule<UserRegistration>(UserRegistration.class);
    private UserRegistration mactivity=null;

    @Before
    public void setUp() throws Exception {
        mactivity=obj.getActivity();
    }
    @Test
    public void testLaunch()
    {
        View v1,v2,v3,v4,v5,v6;
        v1=mactivity.findViewById(R.id.icon);
        v2=mactivity.findViewById(R.id.edt_useremail);
        v3=mactivity.findViewById(R.id.edt_userpassword);
        v4=mactivity.findViewById(R.id.slogan);
        v5=mactivity.findViewById(R.id.btn_signin);
        v6=mactivity.findViewById(R.id.btn_signup);
        assertNotNull(v1);
        assertNotNull(v2);
        assertNotNull(v3);
        assertNotNull(v4);
        assertNotNull(v5);
        assertNotNull(v6);
    }
    @After
    public void tearDown() throws Exception {
        mactivity=null;
    }

}