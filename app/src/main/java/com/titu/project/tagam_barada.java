package com.titu.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class tagam_barada extends AppCompatActivity {
    TextView ady,bahasy,barada,resepti,t1,t2;
    ImageView suartyIV;
    String gelen_at,gelen_baha,gelen_barada,gelen_resepti,id,suraty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagam_barada);
        Intent intent=getIntent();
        gelen_at=intent.getStringExtra("ady");
        gelen_baha=intent.getStringExtra("baha");
        gelen_barada=intent.getStringExtra("barada");
        gelen_resepti=intent.getStringExtra("resepti");
        suraty=intent.getStringExtra("suraty");
        id=intent.getStringExtra("id");
        ImageButton yza=findViewById(R.id.yza);
        yza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Typeface stika=Typeface.createFromAsset(getAssets(),"fonts/Sitka_Banner.ttf");
        init();
        ady.setTypeface(stika);
        bahasy.setTypeface(stika);
        barada.setTypeface(stika);
        resepti.setTypeface(stika);
        t1.setTypeface(stika);
        t2.setTypeface(stika);

        ady.setText(gelen_at);
        bahasy.setText(gelen_baha+" TMT");
        barada.setText(gelen_barada);
        resepti.setText(gelen_resepti);
        Glide.with(this).load(suraty).into(suartyIV);

    }
    private void init(){
        ady=findViewById(R.id.ady);
        bahasy=findViewById(R.id.bahasy);
        barada=findViewById(R.id.barada);
        resepti=findViewById(R.id.resepti);
        suartyIV=findViewById(R.id.tagam_suraty);
        t1=findViewById(R.id.title_barada);
        t2=findViewById(R.id.title_resepti);

    }
}
