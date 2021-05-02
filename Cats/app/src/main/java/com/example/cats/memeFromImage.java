package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        byte[] byteArray = bundle.getByteArray("ByteArray");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        ImageView imageView = findViewById(R.id.meme_image);
        imageView.setImageBitmap(bitmap);
    }
}