package com.example.pye.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pye.DisplayPage.DisplayActivity;
import com.example.pye.HomePageMainActivity.MainActivity;
import com.example.pye.R;
import com.example.pye.UserRegistration;

public class SplashScreen extends AppCompatActivity {
    ImageView imgview;
    Animation animation;
    private static int SPLASH_SCREEN_TIME_OUT=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        imgview=findViewById(R.id.splash_icon);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_picture);
        imgview.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreen.this,
                        UserRegistration.class);

                startActivity(i);

                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
