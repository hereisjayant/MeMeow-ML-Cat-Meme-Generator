package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.opencsv.CSVReader;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
            InputStream is = am.open("vocab.csv");

            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            System.out.println("Starting CSV reader");
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
//                System.out.println(nextLine[0] + " " + nextLine[1]+"till here");

                vocabMap.put(nextLine[0], Long.parseLong(nextLine[1].substring(0, nextLine[1].length() - 1)));
            }
        } catch (IOException e) {
            System.out.println("OOPS error!!");
        }

        // testing
//        System.out.println("Printing the vocabMap:");
//        System.out.println(vocabMap.get("hello"));
//        System.out.println(vocabMap.get("fond"));

        // importing the ML model
        module = Module.load(assetFilePath(this,"MemeSentiment_model.pt"));
//        System.out.println("get the model");

        TextView txt = (TextView) findViewById(R.id.meme_txt);
        if(bundle.getString("MEME-TXT")!= null)
        {
            txt.setText(bundle.getString("MEME-TXT"));
        }
        String txtCaseInsensitive = txt.getText().toString().toLowerCase();
        String userCaptionArray[] = txtCaseInsensitive.split(" ");
//        System.out.println("Printing the user caption array:");
//        for(String element: userCaptionArray){
//            System.out.println(element);
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

        long [] inputTensorShape = {userCaptionIndices.length};
        long [] zeroArr  = {0};
        long [] zeroTensorShape = {1};

        Tensor inputTensor = Tensor.fromBlob(userCaptionIndices, inputTensorShape);
        Tensor zeroTensor = Tensor.fromBlob(zeroArr, zeroTensorShape);

//        System.out.println("Shape of the inputTensor: "+inputTensor.shape()[0]);
//        System.out.println("Data of the inputTensor: "+inputTensor.getDataAsLongArray()[0]+" "+inputTensor.getDataAsLongArray()[1]);

//        long[] inTest = inputTensor.getDataAsLongArray();
//        System.out.println("Printing the input tensor:");
//        for(long element: inTest){
//            System.out.println(element);
//        }

//        System.out.println("Feeding the Model***");

        IValue outModel = module.forward(IValue.from(inputTensor),IValue.from(zeroTensor));

//        System.out.println();

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
                emotion = "sad";
            break;
            case 1:
                emotion = "angry";
            break;
            case 2:
                emotion = "scared";
            break;
            case 3:
                emotion = "happy";
            break;
        }

        Random random = new Random();
        int index = random.nextInt(20);
        ImageView imageView = (ImageView) findViewById(R.id.meme_image);
        int id = getResources().getIdentifier(emotion + index, "drawable", getPackageName());
        imageView.setImageResource(id);
    }
}