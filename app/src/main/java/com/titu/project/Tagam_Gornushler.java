package com.titu.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Tagam_Gornushler extends AppCompatActivity {
    Context context=this;
    ImageButton add_kate;
    ArrayList<kategoriyalar> kategoriyalarArrayList=new ArrayList<>();
    GridView gridView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference tagam_gornushleri=db.collection("tagam_gornushleri");
  //  private DocumentReference tagamRef=db.document("tagam_gornushleri/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagam__gornushler);
        gridView=findViewById(R.id.list_kategoriya);
        add_kate=findViewById(R.id.add_kate);
        add_kate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tagam_Gornushler.this,add_kategoriya.class));
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        tagam_gornushleri.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    return;
                }
                kategoriyalarArrayList.clear();
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    kategoriyalar k=documentSnapshot.toObject(kategoriyalar.class);

                    kategoriyalarArrayList.add(new kategoriyalar(k.getKategoriya(),k.getImage_url(),documentSnapshot.getId()));

                }
                setAdapter adapter=new setAdapter(kategoriyalarArrayList, context);
                //final Animation atg= AnimationUtils.loadAnimation(Tagam_Gornushler.this,R.anim.slide);
                gridView.setAdapter(adapter);
                LayoutAnimationController animationController= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_anim);
                gridView.setLayoutAnimation(animationController);
                gridView.scheduleLayoutAnimation();
               // gridView.startAnimation(atg);

            }
        });
    }

    public void loaddata(){
        tagam_gornushleri.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            kategoriyalar k=documentSnapshot.toObject(kategoriyalar.class);
                            //kategoriyalarArrayList.add(new kategoriyalar(k.getKategoriya(),k.getImage_url()));

                        }

                        setAdapter adapter=new setAdapter(kategoriyalarArrayList, context);
                        gridView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slideleft,R.anim.sliderightout);
    }
}
