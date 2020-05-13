package com.titu.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class setAdapter extends BaseAdapter {
    ArrayList<kategoriyalar> kategoriyalarArrayList=new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    FirebaseFirestore docRef = FirebaseFirestore.getInstance();

    public setAdapter(ArrayList<kategoriyalar> kategoriyalarArrayList, Context context) {
        this.kategoriyalarArrayList = kategoriyalarArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return kategoriyalarArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater=LayoutInflater.from(context);
        final kategoriyalar k=kategoriyalarArrayList.get(position);
        View view=inflater.inflate(R.layout.kategoriyalar,null);
        Typeface stika=Typeface.createFromAsset(context.getAssets(),"fonts/Sitka_Banner.ttf");
        TextView kategoriya=view.findViewById(R.id.kategoriyasy);
        kategoriya.setTypeface(stika);
        LinearLayout container_kategoriyalar=view.findViewById(R.id.container_kategoriyalar);

        ImageView suraty=view.findViewById(R.id.menu_image);
        ImageButton delete_document=view.findViewById(R.id.delete_document);
        ImageButton update_document=view.findViewById(R.id.update_document);
        update_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Kategoriyany_Uytgetmek.class);
                intent.putExtra("Suraty",k.getImage_url());
                intent.putExtra("Kategoriya",k.getKategoriya());
                intent.putExtra("id",k.getDocumentId());
                context.startActivity(intent);
            }
        });
        delete_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference noteRef=docRef.collection("tagam_gornushleri").document(k.getDocumentId());
                noteRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Tagam Gornushi pozuldy",Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,"Tagam Gornushi pozulmady",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        container_kategoriyalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Tagamlar.class);
                intent.putExtra("Kategoriya",k.getKategoriya());
                intent.putExtra("id",k.getDocumentId());
                context.startActivity(intent);
            }
        });
        kategoriya.setText(k.getKategoriya());

        Glide.with(context)
                .load(k.getImage_url())
                .into(suraty);

        return view;
    }
}
