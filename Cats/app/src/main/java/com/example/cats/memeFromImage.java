package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class memeFromImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_from_image);
        Bundle bundle = getIntent().getExtras();
        int id = -1;
        id = bundle.getInt("IMG-ID");
        ImageView imageView = findViewById(R.id.meme_image);
        imageView.setImageResource(id);
    }
}