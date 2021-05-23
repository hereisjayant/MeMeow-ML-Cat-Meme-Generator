package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Float.MAX_VALUE;

public class calledFromMkFromTxt extends AppCompatActivity {

    private Module module = null;
    ProgressDialog pd;


    // Creating a helper method to get the abs path to the model:
    public static String assetFilePath(Context context, String assetName){
        File file = new File(context.getFilesDir(), assetName);
        if(file.exists() && file.length() > 0){
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)){
            try(OutputStream os = new FileOutputStream(file)){
                byte[] buffer = new byte[4 * 1024];
                int read;
                while((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FILE NOT FOUND EXCEPTION 404");
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_called_from_mk_from_txt);
        Bundle bundle = getIntent().getExtras();


        // importing the vocab:
        HashMap <String, Long> vocabMap = new HashMap<>();
        try {
            AssetManager am = getApplicationContext().getAssets();
            InputStream is = am.open("vocab_V2.csv");

            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            System.out.println("Starting CSV reader");
            while ((nextLine = reader.readNext()) != null) {
//                 nextLine[] is an array of values from the line
//                System.out.println(nextLine[0] + " " + nextLine[1]+"till here");

                vocabMap.put(nextLine[0], Long.parseLong(nextLine[1].substring(0, nextLine[1].length() - 1)));
            }
        } catch (IOException e) {
            System.out.println("OOPS error!!");
        }

//         testing
//        System.out.println("Printing the vocabMap:");
//        System.out.println(vocabMap.get("hello"));
//        System.out.println(vocabMap.get("fond"));

        // importing the ML model
        module = Module.load(assetFilePath(this,"MemeSentiment_model_V4.pt"));
        System.out.println("get the model");

        TextView txt = (TextView) findViewById(R.id.meme_txt);
        if(bundle.getString("MEME-TXT")!= null)
        {
            txt.setText(bundle.getString("MEME-TXT"));
        }
        String txtCaseInsensitive = txt.getText().toString().toLowerCase();
        String userCaptionArray[] = txtCaseInsensitive.split("\\s+");

//        System.out.println("Printing the user caption array:");
//        for(String element: userCaptionArray){
//            System.out.println(element+",");
//        }

        long[] userCaptionIndices = new long[userCaptionArray.length];

//        System.out.println("Printing user caption indices:");
        for (int i = 0; i < userCaptionArray.length; i++) {
            if(vocabMap.containsKey(userCaptionArray[i])) {
                userCaptionIndices[i] = (vocabMap.get(userCaptionArray[i]));
            }
            else{
                userCaptionIndices[i] = 0;
            }
//            System.out.println(userCaptionIndices[i]);
        }

        long [] inputTensorShape = {userCaptionIndices.length, 1};
        Tensor inputTensor = Tensor.fromBlob(userCaptionIndices, inputTensorShape);

//        System.out.println("Shape of the inputTensor: "+inputTensor.shape()[0]);
//        System.out.println("Data of the inputTensor: "+inputTensor.getDataAsLongArray()[0]+" "+inputTensor.getDataAsLongArray()[1]);
//
//        long[] inTest = inputTensor.getDataAsLongArray();
//        System.out.println("Printing the input tensor:");
//        for(long element: inTest){
//            System.out.println(element);
//        }

        System.out.println("Feeding the Model***");
        IValue outModel = module.forward(IValue.from(inputTensor));
        float[] scoresModelOut = outModel.toTensor().getDataAsFloatArray();

        System.out.println("The weights of the model are:");
        System.out.println(scoresModelOut[0]);
        System.out.println(scoresModelOut[1]);
        System.out.println(scoresModelOut[2]);
        System.out.println(scoresModelOut[3]);

        int sentimentIndex = 0;
        float max_sentiment = -MAX_VALUE;
        for (int i = 0; i < scoresModelOut.length; i++) {
            if (scoresModelOut[i] > max_sentiment) {
                sentimentIndex = i;
                max_sentiment = scoresModelOut[i];
            }
        }
        String emotion = "";
        switch (sentimentIndex){
            case 0:
                emotion = "happy";
            break;
            case 1:
                emotion = "sad";
            break;
            case 2:
                emotion = "angry";
            break;
            case 3:
                emotion = "scared";
            break;
        }

        Random random = new Random();
        int index = random.nextInt(20);
        ImageView imageView = (ImageView) findViewById(R.id.meme_image);
        int id = getResources().getIdentifier(emotion + index, "drawable", getPackageName());
        imageView.setImageResource(id);
        System.out.println("Sentiment: " + emotion);

        // display meme sentiment
        TextView sentiment_textView = (TextView) findViewById(R.id.meme_sentiment);
        String sentiment = "Your caption was "+emotion+"!";
        sentiment_textView.setText(sentiment);

        /******* Saving the meme ******/

        Button saveBtn = (Button) (findViewById(R.id.save_meme_button));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(calledFromMkFromTxt.this);
                pd.setMessage("saving your image");
                pd.show();
                File file = saveBitMap(calledFromMkFromTxt.this, txt, imageView);
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

        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MeMeow");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
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