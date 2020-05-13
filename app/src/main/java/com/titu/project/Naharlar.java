package com.titu.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Naharlar extends AppCompatActivity {
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naharlar);
        viewPager2=findViewById(R.id.viewPagerImageSlider);
        List<Slider_Item> slider_items=new ArrayList<>();
        slider_items.add(new Slider_Item(R.drawable.fitchi));
        slider_items.add(new Slider_Item(R.drawable.somsa));
        slider_items.add(new Slider_Item(R.drawable.palow));
        slider_items.add(new Slider_Item(R.drawable.towukly_palow));

        viewPager2.setAdapter(new SliderAdapter(slider_items,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r+0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
    }
}
