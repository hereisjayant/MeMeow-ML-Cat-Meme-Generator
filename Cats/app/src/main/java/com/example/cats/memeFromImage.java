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
    String[] captions_angry = {"WHO UNPLUGGED THE OSCILLOSCOPE!",
            "I'm not angry, you're angry.",
            "CPSC IS NOT BETTER THAN CPEN",
            "THIS IS NOT WHAT I SIGNED UP FOR",
            "HOW CAN YOU HATE DINOSAURS!",
            "ITS NOT TRUE TILL PROVEN TRUE",
            "*when you finally see why your code was segfaulting*",
            "WHAT DO YOU MEAN HE'LL NOT COOK TODAY!",
            "WHAT DO YOU MEAN YOU DIDN'T SAVE A DUPLICATE!",
            "YOU CANNOT JUSTIFY MEMORY LEAKS!"
    };
    String[] captions_happy = {"23:59--ASSIGNMENT SUBMITTED!!!",
            "CPSC prof says remaining labs are optional",
            "Me seeing my code finally compiles",
            "Me looking at the webwork perfect",
            "This is my super happy face",
            "Cheer up! Weekend incoming",
            "when I turn to the right page on my first try",
            "Me when I see my crush",
            "When I wake up one minute before the alarm",
            "I am so EXCITED!!"
};
    String[] captions_sad = {"My ram when I open the 113th chrome tab",
            "Profs: “Please don’t be overwhelmed” Me: ok",
            "When you actually have to do hardware in CPEN",
            "When you see CPSC majors playing outside",
            "Checking accuracy after working 35 hrs: -23%",
            "Group discussion: *Everyone’s on mute*",
            "Prof: guys pls... can you hear me...",
            "I’m not crying, you’re crying!",
            "When your “cheat day” turns into “cheat life”",
            "UBC: “Hope this email finds you well” Me:"
    };
    String[] captions_sleepy = {"*Me after pulling 2 all-nighters because CPEN*",
            "“sLeEp dEpRiVaTiOn iS mY pAsSiOn”",
            "“Just 5 more minutes”",
            "*Taking naps = hibernating for 2 days instead*",
            "What are my plans for the summer? Sleep.",
            "*Me 2 mins after joining a lecture*",
            "‘Never stop dreaming,’ they said. Well…",
            "*How I sleep the night before my 3 midterms*",
            "“I’m on my way, be there in 5!”",
            "“Sorry, I can’t, I’m busy tomorrow…”"
    };

//    private String[] getStrings(String class_name ){
//        BufferedReader reader;
//        String[] captions = new String[10];
//        try {
//            String current = new java.io.File( "." ).getCanonicalPath();
//            reader = new BufferedReader(new FileReader(
//                    current+class_name+"_captions.txt"));
//
//            String line = reader.readLine();
//            int i = 0;
//            while (line != null) {
//                captions[i] = line;
//                // read next line
//                line = reader.readLine();
//                i++;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return captions;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_from_image);

        Bundle bundle = getIntent().getExtras();
        byte[] byteArray = bundle.getByteArray("ByteArray");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        textView = findViewById(R.id.meme_txt);

        String name=bundle.getString("CLASS_NAME");
        Random random = new Random();
        int index = random.nextInt(10);

        ImageView imageView = findViewById(R.id.meme_image);
        imageView.setImageBitmap(bitmap);

        textView.setTextSize(45);
        System.out.println("?"+name+"?");

        if(name.equals("sleepy")){
            System.out.println("!!!IS: " + name);
            textView.setText(captions_sleepy[index]);
        }
        if(name.equals("sad")){
            System.out.println("!!!IS: " + name);
            textView.setText(captions_sad[index]);
        }
        if(name.equals("happy")){
            System.out.println("!!!IS: " + name);
            textView.setText(captions_happy[index]);
        }
        if(name.equals("angry")){
            System.out.println("!!!IS: " + name);
            textView.setText(captions_angry[index]);
        }

//        textView.setText(strings[index]);
    }
}
