package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class memeFromImage extends AppCompatActivity {

    ProgressDialog pd;
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

        System.out.println("?"+name+"?");

        if(name.equals("sad")){
            System.out.println("!!!IS: " + name);
            textView.setText(captions_sad[index]);
        }
        if(name.equals("happy")){
            System.out.println("!!!IS: " + name);
            textView.setText(captions_happy[index]);
        }

        // display meme image
        TextView sentiment_textView = (TextView) findViewById(R.id.meme_sentiment);
        String sentiment = "Your image was "+name+"!";
        sentiment_textView.setText(sentiment);

//        textView.setText(strings[index]);
        /******* Saving the meme ******/

        Button saveBtn = (Button) (findViewById(R.id.save_IMG_meme_button));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(memeFromImage.this);
                pd.setMessage("saving your image");
                pd.show();
                File file = saveBitMap(memeFromImage.this, textView, imageView);
                pd.cancel();
                if (file != null) {
                    Log.i("TAG", "Drawing saved to the gallery!");
                } else {
                    Log.i("TAG", "Oops! Image could not be saved.");

                }
            }
        });
    }

    private Bitmap getBitmapFromView(TextView txt_view, ImageView image_view) {
        System.out.println("In getBitmapFromView");
        System.out.println("save meme button pressed");
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(image_view.getRootView().getWidth(),
                image_view.getRootView().getHeight(), Bitmap.Config.ARGB_8888);
//        Bitmap imageBitmap = Bitmap.createBitmap(image_view.getWidth(),
//                image_view.getHeight(), Bitmap.Config.ARGB_8888);

        System.out.println("Bitmap created");
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        canvas.drawColor(Color.WHITE);
        View v1 = image_view.getRootView();
//        v1.setDrawingCacheEnabled(true);
//        bitmap = canvas.drawBitmap(v1.getDrawingCache(),0,0,v1.getWidth(),v1.getHeight());
//        v1.setDrawingCacheEnabled(false);
        v1.draw(canvas);
//        txt_view.draw(canvas);
//        canvas.drawBitmap(imageBitmap, 0, txt_view.getHeight(), null);
//        image_view.draw(canvas);
        //return the bitmap
        int[] topLeftTxt = new int[2];
        int[] topLeftImg = new int[2];
        txt_view.getLocationOnScreen(topLeftTxt);
        image_view.getLocationOnScreen(topLeftImg);

        Bitmap resizedBitmap = Bitmap.createBitmap(returnedBitmap, topLeftImg[0], topLeftTxt[1], image_view.getWidth(), image_view.getHeight() + txt_view.getHeight());
        return resizedBitmap;
    }

    private File saveBitMap(Context context, TextView txt_view, ImageView image_view){
        System.out.println("In saveBitMap");

        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Logicchip");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(txt_view, image_view);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }

    private void scanGallery(Context cntx, String path) {
        System.out.println("In scanGallery");

        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }


}
