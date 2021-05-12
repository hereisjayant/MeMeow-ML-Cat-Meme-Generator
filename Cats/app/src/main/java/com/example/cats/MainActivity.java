package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String str = "Kaboom";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mkFromTxtBtn = (Button) findViewById(R.id.mkFromTxtBtn);
        mkFromTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textEntering = new Intent(getApplicationContext(), TextEnteringActivity.class);
                textEntering.putExtra("com.example.cats.meme.text",str);
                startActivity(textEntering);
            }
        });

        Button mkFromImBtn = (Button) findViewById(R.id.mkFromImBtn);
        mkFromImBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imEntering = new Intent(getApplicationContext(), imageUpload.class);
                imEntering.putExtra("com.example.cats.meme.text",str);
                startActivity(imEntering);
            }
        });

        Button infoBtn = (Button) findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(getApplicationContext(), info.class);
                info.putExtra("com.example.cats.meme.text",str);
                startActivity(info);
            }
        });
    }
}