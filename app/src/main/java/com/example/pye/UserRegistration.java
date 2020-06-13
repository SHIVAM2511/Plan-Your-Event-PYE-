package com.example.pye;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pye.Activity.SignUp;
import com.example.pye.Common.Common;
import com.example.pye.DisplayPage.DisplayActivity;
import com.example.pye.Model.UserModel;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserRegistration extends AppCompatActivity {
    private AlertDialog dialog;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Button btn_sign_up;
    private Button btn_sign_in;
    EditText edt_email,edt_password;
    private FirebaseAuth mAuth;
    private  DatabaseReference userReference;


    @Override
    protected void onStart() {
        super.onStart();
        if( Common.currentUser!=null)
        {
            startActivity(new Intent(UserRegistration.this,DisplayActivity.class));
            finish();
        }

    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        userReference=database.getReference(Common.USER_REFERENCES);
       // init();
        btn_sign_up=(Button) findViewById(R.id.btn_signup);
        btn_sign_in=(Button)findViewById(R.id.btn_signin);
        edt_email=findViewById(R.id.edt_useremail);
        edt_password=findViewById(R.id.edt_userpassword);
        mAuth=FirebaseAuth.getInstance();
        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();



        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent signUp=new Intent(UserRegistration.this, SignUp.class);
                startActivity(signUp);

            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.show();
                String email=edt_email.getText().toString().trim();
                String password=edt_password.getText().toString().trim();


                if(TextUtils.isEmpty(email))
                {
                    edt_email.setError("Please enter email id");
                    edt_email.requestFocus();
                    dialog.dismiss();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    edt_email.setError("Please enter valid email id");
                    edt_email.requestFocus();
                    dialog.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    edt_password.setError("Please enter password");
                    edt_password.requestFocus();
                    dialog.dismiss();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(UserRegistration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
                                        String uid=mAuth.getCurrentUser().getUid();
                                        userReference.child(uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                UserModel user=dataSnapshot.getValue(UserModel.class);
                                                goToDisplayActivity(user);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                dialog.dismiss();
                                                Toast.makeText(UserRegistration.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                }
                                else
                                {
                                    dialog.dismiss();
                                    Toast.makeText(UserRegistration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

    }

    private void goToDisplayActivity(UserModel userModel) {
        Common.currentUser=userModel;
        dialog.dismiss();
        startActivity(new Intent(UserRegistration.this,DisplayActivity.class));
        finish();
    }


}
