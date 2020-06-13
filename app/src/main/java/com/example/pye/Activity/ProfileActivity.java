package com.example.pye.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pye.Common.Common;
import com.example.pye.DisplayPage.DisplayActivity;
import com.example.pye.Model.UserModel;
import com.example.pye.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference ref,ref1;
    TextView username;
    ImageView userImage;
    EditText edt_username,edt_useremail,edt_useraddress,edt_userphone;
    String user_name,user_phone,user_address,user_email,user_password;
    ImageButton update_profile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userImage=findViewById(R.id.image_user);
        username=findViewById(R.id.txt_username);
        edt_username=findViewById(R.id.edt_profile_name);
        edt_useraddress=findViewById(R.id.edt_profile_address);
        edt_useremail=findViewById(R.id.edt_profile_email);
        edt_userphone=findViewById(R.id.edt_profile_phone);
        update_profile=findViewById(R.id.update_details);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String uid=firebaseAuth.getCurrentUser().getUid();

        ref= FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(DisplayActivity.this, "Inside", Toast.LENGTH_SHORT).show();
                user_name=dataSnapshot.child(uid).child("name").getValue().toString();
                user_phone=dataSnapshot.child(uid).child("phone").getValue().toString();
                user_address=dataSnapshot.child(uid).child("address").getValue().toString();
                user_email=dataSnapshot.child(uid).child("email").getValue().toString();
                user_password=dataSnapshot.child(uid).child("password").getValue().toString();
                username.setText(user_name);
                edt_username.setText(user_name);
                edt_useraddress.setText(user_address);
                edt_useremail.setText(user_email);
                edt_userphone.setText(user_phone);
                UserModel userModel=new UserModel(uid,user_name,user_address,user_phone,user_password, user_email);
                Common.currentUser=userModel;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ref1=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,address,email,phone;
                name=edt_username.getText().toString();
                email=edt_useremail.getText().toString();
                phone=edt_userphone.getText().toString();
                address=edt_useraddress.getText().toString();
                UserModel userModel=new UserModel(uid,name,address,phone,user_password,email);
                ref1.setValue(userModel);
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
