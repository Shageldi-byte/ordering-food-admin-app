package com.titu.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Kategoriyalar_Ac extends AppCompatActivity {
    List<kategoriyalar> kategoriyalarList=new ArrayList<>();
    Context context=this;
    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoriyalar_);
        rec=findViewById(R.id.rec);







    }
}
