package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class memeFromImage extends AppCompatActivity {

    private TextView textView;

    private String[] getStrings(String class_name) throws IOException {
        BufferedReader reader;
        String[] captions = new String[10];
        String current = new java.io.File(class_name + "_captions.txt").getCanonicalPath();

        try {
            textView.setText("line 24");
            reader = new BufferedReader(new FileReader(
                    current));
            textView.setText("line 26");
//            String line = reader.readLine();
//            int i = 0;t
//            textView.setText("line 29");
//            while (line != null) {
//                captions[i] = line;
//                // read next line
//                line = reader.readLine();
//                i++;
//            }
//            textView.setText("line 36");
//            reader.close();
//            textView.setText("line 38");
        } catch (IOException e) {
            e.printStackTrace();
            textView.setText("aaaahhhh");
        }
        return captions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_from_image);

        Bundle bundle = getIntent().getExtras();
        byte[] byteArray = bundle.getByteArray("ByteArray");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        textView = findViewById(R.id.meme_txt);
//        textView.setTextSize(25);
        textView.setText(bundle.getString("CLASS_NAME"));

        try {
            String[] strings = getStrings(bundle.getString("CLASS_NAME"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Random random = new Random();
//        int index = random.nextInt(strings.length);
//
        ImageView imageView = findViewById(R.id.meme_image);
        imageView.setImageBitmap(bitmap);
//
//        textView.setText(strings[index]);
    }
}
