package com.titu.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tagamlar extends AppCompatActivity {
    ImageButton add_tagam1;
    String kategoriya;
    ArrayList<tagam_class> arrayList=new ArrayList<>();
    ArrayList<tagam_class> gozleg_list=new ArrayList<>();
    Context context=this;
    GridView gridView;
    EditText gozleg;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference tagamlar=db.collection("tagamlar");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagamlar);
        add_tagam1=findViewById(R.id.add_tagam);
        gridView=findViewById(R.id.list_tagamlar);
        gozleg=findViewById(R.id.gozleg);
        final Intent intent=getIntent();
        ImageButton yza=findViewById(R.id.yza);
        final Animation atg= AnimationUtils.loadAnimation(this,R.anim.zoom_an);
        yza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        kategoriya=intent.getStringExtra("Kategoriya");
        //Toast.makeText(this,kategoriya,Toast.LENGTH_SHORT).show();
        add_tagam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Tagamlar.this, add_tagam.class);
                intent1.putExtra("Kategoriya",kategoriya);
                startActivity(intent1);
            }
        });
        gozleg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    setTagamlar adapter = new setTagamlar(arrayList, context);
                    gridView.setAdapter(adapter);
                    LayoutAnimationController animationController= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_anim);
                    gridView.setLayoutAnimation(animationController);
                    gridView.scheduleLayoutAnimation();
                }
                else {
                    gozleg_list.clear();
                    for (int i = 0; i < arrayList.size(); i++) {

                        tagam_class tagam = arrayList.get(i);
                        if (tagam.getAdy().toLowerCase().contains(s.toString().toLowerCase())) {
                            gozleg_list.add(new tagam_class(tagam.getId(), tagam.getAdy(), tagam.getBahasy(), tagam.getSuraty(), tagam.getBarada(), tagam.getResepti(), tagam.getGornushi()));

                        }
                    }

                    setTagamlar adapter = new setTagamlar(gozleg_list, context);
                    gridView.setAdapter(adapter);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (gozleg.getText().toString().isEmpty()) {
            tagamlar.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
                    arrayList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    Map<String, Object> tagamlar = new HashMap<>();
//                    tagamlar=documentSnapshot.getData();
                        String ady = documentSnapshot.getData().get("ady").toString();
                        String bahasy = documentSnapshot.getData().get("bahasy").toString();
                        String barada = documentSnapshot.getData().get("barada").toString();
                        String resepti = documentSnapshot.getData().get("resepti").toString();
                        String gornushi = documentSnapshot.getData().get("gornushi").toString();
                        String suraty = documentSnapshot.getData().get("suraty").toString();


                        // tagam_class k=documentSnapshot.toObject(tagam_class.class);
                        //k.setDocumentId(documentSnapshot.getId());
                        //Toast.makeText(getApplicationContext(), ,Toast.LENGTH_SHORT).show();
                        if (gornushi.equals(kategoriya)) {
                            arrayList.add(new tagam_class(documentSnapshot.getId(), ady, bahasy, suraty, barada, resepti, gornushi));
                        }


                    }
                    setTagamlar adapter = new setTagamlar(arrayList, context);
                    gridView.setAdapter(adapter);
                    LayoutAnimationController animationController= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_anim);
                    gridView.setLayoutAnimation(animationController);
                    gridView.scheduleLayoutAnimation();


                }
            });
        }
    }
}
