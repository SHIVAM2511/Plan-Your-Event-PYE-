package com.example.pye.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

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

import com.example.pye.Common.Common;
import com.example.pye.DisplayPage.DisplayActivity;
import com.example.pye.Model.UserModel;
import com.example.pye.R;
import com.example.pye.UserRegistration;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    Button btn_sign_up;
    EditText edt_name,edt_phone,edt_address,edt_password,edt_passcon,edt_email;
    private AlertDialog dialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;
    private FirebaseDatabase database;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn_sign_up=findViewById(R.id.btn_sign_up);
        edt_passcon=findViewById(R.id.edt_userpasswordcon);
        edt_name=findViewById(R.id.edt_username);
        edt_phone=findViewById(R.id.edt_userphone);
        edt_address=findViewById(R.id.edt_useraddress);
        edt_email=findViewById(R.id.edt_useremail);
        edt_password=findViewById(R.id.edt_userpassword);
        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();

        firebaseAuth=FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Common.USER_REFERENCES);


        btn_sign_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.show();
                String name=edt_name.getText().toString().trim();
                String address=edt_address.getText().toString().trim();
                String phone=edt_phone.getText().toString().trim();
                String password=edt_password.getText().toString().trim();
                String passwordcon=edt_passcon.getText().toString().trim();
                String email=edt_email.getText().toString().trim();



                if(TextUtils.isEmpty(phone))
                {
                    edt_phone.setError("Please enter contact number");
                    edt_phone.requestFocus();
                    dialog.dismiss();
                    return;
                }
                if(!Patterns.PHONE.matcher(phone).matches())
                {
                    edt_phone.setError("Please enter valid contact number");
                    edt_phone.requestFocus();
                    dialog.dismiss();
                    return;
                }

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

                if(TextUtils.isEmpty(name))
                {
                    edt_name.setError("Please enter name");
                    edt_name.requestFocus();
                    dialog.dismiss();
                    return;
                }


                if(TextUtils.isEmpty(address))
                {
                    edt_address.setError("Please enter address");
                    edt_address.requestFocus();
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
                if(TextUtils.isEmpty(passwordcon))
                {
                    edt_passcon.setError("Please confirm password");
                    edt_passcon.requestFocus();
                    dialog.dismiss();
                    return;
                }
                if(password.length()<6)
                {
                    edt_password.setError("Password length too short");
                    edt_password.requestFocus();
                    dialog.dismiss();
                    return;
                }

                if(password.equals(passwordcon))
                {


                    
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful())
                                     {
                                         dialog.dismiss();
                                         UserModel userModel=new UserModel(firebaseAuth.getCurrentUser().getUid(),name,address,phone,password,email);
                                         FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCES)
                                                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                 .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(SignUp.this, "User registration successful", Toast.LENGTH_SHORT).show();
                                                    goToUserRegistrationActivity(userModel);
                                                }
                                                 else
                                                 {
                                                     dialog.dismiss();
                                                     Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         });


                                     }
                                     else
                                     {
                                         dialog.dismiss();
                                         Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                }
                            });

                    /////
                }else
                {
                    Toast.makeText(SignUp.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }

    private void goToUserRegistrationActivity(UserModel userModel) {
        Common.currentUser=userModel;
        startActivity(new Intent(SignUp.this,UserRegistration.class));
        finish();

    }
}
