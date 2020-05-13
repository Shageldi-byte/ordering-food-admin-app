package com.titu.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Tagamlary_Uytgetmek extends AppCompatActivity {
    String ady,baha,barada,resepti,suraty,id;
    EditText adyed,bahaed,baradaed,reseptied;
    ImageView suratyIV;
    Button uytgetmek;
    Uri uri;
    private Firebase mRef;
    StorageReference mStorage;
    ProgressDialog progressDialog;
    FirebaseFirestore docRef = FirebaseFirestore.getInstance();
    private static  final int GALLERY_INTENT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagamlary__uytgetmek);
        init();
        Intent intent=getIntent();
        ady=intent.getStringExtra("ady");
        baha=intent.getStringExtra("baha");
        barada=intent.getStringExtra("barada");
        resepti=intent.getStringExtra("resepti");
        suraty=intent.getStringExtra("suraty");
        id=intent.getStringExtra("id");
        adyed.setText(ady);
        bahaed.setText(baha);
        baradaed.setText(barada);
        reseptied.setText(resepti);
        ImageButton yza=findViewById(R.id.yza);
        yza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Glide.with(this).load(suraty).into(suratyIV);
        progressDialog=new ProgressDialog(this);
        mStorage= FirebaseStorage.getInstance().getReference();

        uytgetmek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adyed.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tagam adyny giriziň",Toast.LENGTH_SHORT).show();
                    return;
                }if(bahaed.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tagam bahasyny giriziň",Toast.LENGTH_SHORT).show();
                    return;
                }if(reseptied.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tagam reseptini giriziň",Toast.LENGTH_SHORT).show();
                    return;
                }if(baradaed.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tagam adyny giriziň",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (uri != null) {
                    progressDialog.setMessage("Ugradylýar...");
                    progressDialog.show();
                    final StorageReference filpath = mStorage.child("Tagamlar").child(uri.getLastPathSegment());
                    filpath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return filpath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                DocumentReference noteRef = docRef.collection("tagamlar").document(id);

                                Map<String, Object> user = new HashMap<>();
                                user.put("ady", adyed.getText().toString());
                                user.put("bahasy", bahaed.getText().toString());
                                user.put("barada", baradaed.getText().toString());
                                user.put("resepti", reseptied.getText().toString());
                                user.put("suraty", downloadUri.toString());
                                noteRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Tagamlary_Uytgetmek.this,"Uytgedildi",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Tagamlary_Uytgetmek.this,"Uytgedilmedi",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                                progressDialog.dismiss();



                            }
                        }
                    });

                }
                else
                if (adyed.getText().toString() != ady && baradaed.getText().toString()!=baha && baradaed.getText().toString()!=barada && reseptied.getText().toString()!=resepti) {
                    DocumentReference noteRef = docRef.collection("tagamlar").document(id);

                    Map<String, Object> user = new HashMap<>();
                    user.put("ady", adyed.getText().toString());
                    user.put("bahasy", bahaed.getText().toString());
                    user.put("barada", baradaed.getText().toString());
                    user.put("resepti", reseptied.getText().toString());

                    noteRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(Tagamlary_Uytgetmek.this,"Uytgedildi",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Tagamlary_Uytgetmek.this,"Uytgedilmedi",Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressDialog.dismiss();

                }
            }
        });

        suratyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });






    }

    private void init(){
        adyed=findViewById(R.id.kate);
        bahaed=findViewById(R.id.bahasy);
        baradaed=findViewById(R.id.barada);
        reseptied=findViewById(R.id.resepti);
        suratyIV=findViewById(R.id.button2);
        uytgetmek=findViewById(R.id.uytgetmek);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            uri=data.getData();
            suratyIV.setImageURI(uri);

        }
    }
}
