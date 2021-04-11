package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class calledFromMkFromTxt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_called_from_mk_from_txt);
        Bundle bundle = getIntent().getExtras();
        TextView txt = (TextView) findViewById(R.id.meme_txt);
        if(bundle.getString("MEME-TXT")!= null)
        {
            txt.setText(bundle.getString("MEME-TXT"));
        }
        String emotion = "sleepy";
        Random random = new Random();
        int index = random.nextInt(20);
        ImageView imageView = (ImageView) findViewById(R.id.meme_image);
        int id = getResources().getIdentifier(emotion + index, "drawable", getPackageName());
        imageView.setImageResource(id);
    }
}