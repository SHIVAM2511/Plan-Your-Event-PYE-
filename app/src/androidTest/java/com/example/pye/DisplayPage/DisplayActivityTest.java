package com.example.pye.DisplayPage;

import android.view.View;

import com.example.pye.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class DisplayActivityTest {

    @Rule
    public ActivityTestRule<DisplayActivity> obj=new ActivityTestRule<DisplayActivity>(DisplayActivity.class);
    private DisplayActivity mactivity=null;

    @Before
    public void setUp() throws Exception {
        mactivity=obj.getActivity();
    }
    @Test
    public void testLaunch()
    {
        View v1;
        v1=mactivity.findViewById(R.id.nav_view);
        assertNotNull(v1);
    }
    @After
    public void tearDown() throws Exception {
        mactivity=null;
    }
}