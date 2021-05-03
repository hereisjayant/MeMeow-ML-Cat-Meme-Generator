package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ImEnteringActivity extends AppCompatActivity {

    private Bundle extras=getIntent().getExtras();
    //if(extras!=null){ //gives an error
    private String value=extras.getString("KEY");

    private TextView textView;

    //}
    private String[] getStrings(){
        BufferedReader reader;
        String[] captions = new String[10];
        try {
            reader = new BufferedReader(new FileReader(
                    "/Cats/app/src/main/assets/"+value+"_captions.txt"));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                captions[i] = line;
                // read next line
                line = reader.readLine();
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return captions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_entering);
//        String[] strings = getStrings();
        String[] strings = {"OK Boomer", "Kaboom", "WHO UNPLUGGED THE OSCILLOSCOPE!!"};
        Random random = new Random();
        int index = random.nextInt(strings.length);

//        ImageView imageView = (ImageView) findViewById(R.id.meme_image);
//        int id = getResources().getIdentifier("wp", "drawable", getPackageName());
//        imageView.setImageResource(id);

        byte[] byteArray = extras.getByteArray("ByteArray");

        Intent intent = new Intent(getApplicationContext(),memeFromImage.class);
        intent.putExtra("Caption",strings[index]);
        intent.putExtra("ByteArray",byteArray);
        startActivity(intent);

    }

}