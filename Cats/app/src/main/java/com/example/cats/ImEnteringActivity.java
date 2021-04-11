package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ImEnteringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_entering);
        String[] strings = {"OK Boomer", "Kaboom", "WHO UNPLUGGED THE OSCILLOSCOPE!!"};
        Random random = new Random();
        int index = random.nextInt(strings.length);

        TextView textView = (TextView) findViewById(R.id.meme_txt);
        textView.setText(strings[index]);

        ImageView imageView = (ImageView) findViewById(R.id.meme_image);
        int id = getResources().getIdentifier("wp", "drawable", getPackageName());
        imageView.setImageResource(id);
    }
}