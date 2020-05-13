package com.titu.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String username,password,number,email;
    TextView yitirdim;
    private DocumentReference admin = db.collection("admin").document("admin");
    EditText user,pass;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // startActivity(new Intent(this,Naharlar.class));
        Button login_btn = findViewById(R.id.login_btn);
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        yitirdim=findViewById(R.id.yitirdim);
        yitirdim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                admin.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    username=documentSnapshot.getString("username");
                                    password=documentSnapshot.getString("password");
                                    number=documentSnapshot.getString("phone_number");
                                    email=documentSnapshot.getString("email");
                                    //Toast.makeText(MainActivity.this,username+":"+password,Toast.LENGTH_SHORT).show();
                                    if(user.getText().toString().equals(username) && pass.getText().toString().equals(password)){
                                        startActivity(new Intent(MainActivity.this,Tagam_Gornushler.class));
                                        overridePendingTransition(R.anim.slideright,R.anim.slideleftout);
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Ulanyjy adyňyz ýa-da açar belgiňiz nädogry!!!\nGaýtadan synanşyň!",Toast.LENGTH_LONG).show();
                                    }
                                }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Maglumatlar ýüklenmedi!!!",Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

    }

    protected void sendSMSMessage() {
        admin.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    username=documentSnapshot.getString("username");
                    password=documentSnapshot.getString("password");
                    number=documentSnapshot.getString("phone_number");
                    email=documentSnapshot.getString("email");
                    //Toast.makeText(MainActivity.this,username+":"+password,Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Maglumatlar ýüklenmedi!!!",Toast.LENGTH_LONG).show();

                    }
                });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, "Salam "+username+"!!!Siziň ulanyjy adyňyz: "+username+", açar sözüňiz: "+password, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}
