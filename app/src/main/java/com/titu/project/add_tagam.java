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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_tagam extends AppCompatActivity {
    ArrayList<kategoriyalar> arrayList;
    Button add;
    ImageView button2;
    private Firebase mRef;
    ProgressDialog progressDialog;
    StorageReference mStorage;
    EditText ady,baha,barada,resepti;
    String gornushi="salatlar";
    ArrayList<String> tagam=new ArrayList<>();
    ArrayList<String> img=new ArrayList<>();
    Spinner tagam_gornushi;
    Uri uri;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference tagam_gornushleri=db.collection("tagam_gornushleri");
    private static  final int GALLERY_INTENT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tagam);
        button2=findViewById(R.id.button2);
        add=findViewById(R.id.add);
        Intent intent=getIntent();
        ImageButton yza=findViewById(R.id.yza);
        yza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        gornushi=intent.getStringExtra("Kategoriya");
        init();
        mStorage= FirebaseStorage.getInstance().getReference();
        tagam_gornushi=findViewById(R.id.tagam_gornushi);
        progressDialog=new ProgressDialog(this);
       getCustomList();
         tagam_gornushi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 kategoriyalar item=(kategoriyalar) parent.getSelectedItem();
                 //Toast.makeText(add_tagam.this,item.getKategoriya(),Toast.LENGTH_SHORT).show();
               //  gornushi=item.getKategoriya();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


                            Map<String, Object> user = new HashMap<>();
                            user.put("ady", ady.getText().toString());
                            user.put("bahasy", baha.getText().toString());
                            user.put("barada", barada.getText().toString());
                            user.put("resepti", resepti.getText().toString());
                            user.put("gornushi",gornushi);
                            user.put("suraty", downloadUri.toString());


// Add a new document with a generated ID
                            db.collection("tagamlar")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(getApplicationContext(),"Ugradyldy", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Ugradylmady", Toast.LENGTH_SHORT).show();
                                        }
                                    });



                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void getCustomList() {
        arrayList=new ArrayList<>();
        tagam_gornushleri.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            kategoriyalar k=documentSnapshot.toObject(kategoriyalar.class);
                            //kategoriyalarArrayList.add(new kategoriyalar(k.getKategoriya(),k.getImage_url()));
                            //Toast.makeText(getApplicationContext(),k.getKategoriya(),Toast.LENGTH_SHORT).show();
                           // arrayList.add(new CustomItem(k.getKategoriya(),R.drawable.icon));
                            arrayList.add(new kategoriyalar(k.getKategoriya(),k.getImage_url(),documentSnapshot.getId()));


                        }
                        Custom_Spinner adapter1=new Custom_Spinner(getApplicationContext(),arrayList);
                        tagam_gornushi.setAdapter(adapter1);

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

    private void init(){
        ady=findViewById(R.id.kate);
        baha=findViewById(R.id.bahasy);
        barada=findViewById(R.id.barada);
        resepti=findViewById(R.id.resepti);
    }
}
