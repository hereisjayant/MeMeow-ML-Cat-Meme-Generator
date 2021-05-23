package com.example.MeMeow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TextEnteringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_entering);

        EditText txt = (EditText) (findViewById(R.id.inputEditText));
        Button makeBtn = (Button) (findViewById(R.id.CreateBtn));
        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = txt.getText().toString();
                Intent intent = new Intent(getApplicationContext(), calledFromMkFromTxt.class);
                intent.putExtra("MEME-TXT",str);
                //TextView textView = (TextView) findViewById(R.id.meme_txt);
                //textView.setText(str);
                startActivity(intent);
            }
        });
    }
}