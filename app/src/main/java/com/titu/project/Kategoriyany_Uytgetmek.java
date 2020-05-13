package com.titu.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class Kategoriyany_Uytgetmek extends AppCompatActivity {
    ImageView button2;
    String surat,kategoriya,id;
    EditText kate;
    private Firebase mRef;
    StorageReference mStorage;
    ProgressDialog progressDialog;
    Uri uri;

    FirebaseFirestore docRef = FirebaseFirestore.getInstance();
    Button update;
    private static  final int GALLERY_INTENT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoriyany__uytgetmek);
        Intent intent=getIntent();
        mStorage= FirebaseStorage.getInstance().getReference();
        surat=intent.getStringExtra("Suraty");
        kategoriya=intent.getStringExtra("Kategoriya");
        id=intent.getStringExtra("id");
        button2=findViewById(R.id.button2);
        update=findViewById(R.id.update_btn);
        kate=findViewById(R.id.kate);
        ImageButton yza=findViewById(R.id.yza);
        yza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog=new ProgressDialog(this);
        Glide.with(this)
                .load(surat).into(button2);
        kate.setText(kategoriya);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Ugradylýar...");
                progressDialog.show();
                if (uri != null) {
                    final StorageReference filpath = mStorage.child(uri.getLastPathSegment());
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
                                DocumentReference noteRef = docRef.collection("tagam_gornushleri").document(id);

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("kategoriya", kate.getText().toString());
                                    user.put("image_url", downloadUri.toString());
                                    noteRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Kategoriyany_Uytgetmek.this,"Uytgedildi",Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Kategoriyany_Uytgetmek.this,"Uytgedilmedi",Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });



                            }
                        }
                    });

                }
                else
                if (kate.getText().toString() != kategoriya && uri == null) {
                    DocumentReference noteRef = docRef.collection("tagam_gornushleri").document(id);
                    Map<String, Object> user = new HashMap<>();
                    user.put("kategoriya", kate.getText().toString());
                    noteRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(Kategoriyany_Uytgetmek.this,"Uytgedildi",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Kategoriyany_Uytgetmek.this,"Uytgedilmedi",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

    });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            uri=data.getData();
            button2.setImageURI(uri);

        }
    }
}
