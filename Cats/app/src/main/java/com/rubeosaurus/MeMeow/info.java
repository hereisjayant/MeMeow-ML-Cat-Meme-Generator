package com.rubeosaurus.MeMeow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

//        TextView info_txt = (TextView) findViewById(R.id.info_txt);
//        String str = "Team Rubeosaurus spent a month bringing to you MeMeow, an ML-based Cat Meme Generator app for Android. The application allows you to convert the image of a cat into a meme. Our Image Classifying model recognizes the sentiments of the cat in order to bring you an interesting meme. But not just that, we also allow you to make a meme from text (works well for people without cats). Our Text Sentiment Analysis model classifies your text into one of four sentiments - happy, sad, angry, and scared - and creates a cat meme based on that. Either way, you get cat memes and are guaranteed peak entertainment!";
//        info_txt.setText(str);
    }
}