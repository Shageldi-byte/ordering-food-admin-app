package com.titu.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class setTagamlar extends BaseAdapter {
    private ArrayList<tagam_class> arrayList=new ArrayList<>();
    private Context context;
    private FirebaseFirestore docRef = FirebaseFirestore.getInstance();
    public setTagamlar(ArrayList<tagam_class> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("CheckResult")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint({"ViewHolder", "InflateParams"}) View view= inflater.inflate(R.layout.tagamlar_design,null);
        final tagam_class tagam=arrayList.get(position);
        Typeface stika=Typeface.createFromAsset(context.getAssets(),"fonts/Sitka_Banner.ttf");
        ImageView suraty=view.findViewById(R.id.suraty);
        TextView ady=view.findViewById(R.id.ady);
        TextView bahasy=view.findViewById(R.id.baha);
        bahasy.setTypeface(stika);
        ady.setTypeface(stika);
        ImageButton delete_document=view.findViewById(R.id.delete_document);
        ImageButton update_document=view.findViewById(R.id.update_document);
        Glide.with(context).load(tagam.getSuraty()).into(suraty);
        //Toast.makeText(context,tagam.getSuraty(),Toast.LENGTH_SHORT).show();
        ady.setText(tagam.getAdy());
        bahasy.setText(tagam.getBahasy()+" TMT");

        update_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Tagamlary_Uytgetmek.class);
                intent.putExtra("ady",tagam.getAdy());
                intent.putExtra("baha",tagam.getBahasy());
                intent.putExtra("barada",tagam.getBarada());
                intent.putExtra("resepti",tagam.getResepti());
                intent.putExtra("suraty",tagam.getSuraty());
                intent.putExtra("id",tagam.getId());
                context.startActivity(intent);
            }
        });

        delete_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference noteRef=docRef.collection("tagamlar").document(tagam.getId());
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

        LinearLayout tagam1=view.findViewById(R.id.tagam);
        tagam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,tagam_barada.class);
                intent.putExtra("ady",tagam.getAdy());
                intent.putExtra("baha",tagam.getBahasy());
                intent.putExtra("barada",tagam.getBarada());
                intent.putExtra("resepti",tagam.getResepti());
                intent.putExtra("suraty",tagam.getSuraty());
                intent.putExtra("id",tagam.getId());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
