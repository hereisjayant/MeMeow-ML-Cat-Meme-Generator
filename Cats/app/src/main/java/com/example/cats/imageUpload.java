package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class imageUpload extends AppCompatActivity {
    /**
     * Source : https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
     */
    private Bitmap bitmap = null;
    private Module module = null;

    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }
        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    // One Button
    Button BSelectImage;
    // One Preview Image
    ImageView IVPreviewImage;
    TextView textView;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        // register the UI widgets with their appropriate IDs
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        textView = findViewById(R.id.category);

        // loading the image model
        System.out.println("before loading");
        try {
            module = Module.load(assetFilePath(this, "cpu_model_9may130.pt"));
        }catch(IOException e){
            System.out.println("***Model couldn't be loaded***");
            finish();
        }
        System.out.println("Model loaded");

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
                System.out.println("Image chooser");; }
        });

        Button memeBtn = (Button) findViewById(R.id.memeBtn);
        memeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int imgId = IVPreviewImage.getId();
                IVPreviewImage.buildDrawingCache();
                bitmap = IVPreviewImage.getDrawingCache();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50, bs);
                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,224,224,true);

                textView.setTextSize(25);
//                try {
//                    // kitten.jpg should be changed to selectedImageUri
//                    bitmap = BitmapFactory.decodeStream(getAssets().open("kitten.jpg"));
//                    textView.setText("bitmap");
//                }catch(IOException e){
//                    Log.e("PTRTDryRun", "Error reading assets", e);
//                    textView.setText("rip bitmap");
//                    finish();
//                }
                Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor
                        (bitmap2, new float[]{0.5f, 0.5f, 0.5f}, new float[]{0.5f, 0.5f, 0.5f});
                System.out.println("Input tensor here: ");

                float[] inputFloatArr = inputTensor.getDataAsFloatArray();

//                for(float ele: inputFloatArr){
//                    System.out.println(ele);
//                }


                System.out.println("Input tensor shape: ");
                System.out.println(inputTensor.shape()[0] + ", " + inputTensor.shape()[1]+ ", "+ inputTensor.shape()[2]+ ", " +inputTensor.shape()[3]);
                Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
                System.out.println("Output tensor shape: ");
                System.out.println(outputTensor.shape()[0] + ", " + outputTensor.shape()[1]);
                // converting the tensor to float array
                float[] scores = outputTensor.getDataAsFloatArray();
                // printing out the output scores
                System.out.println("these are the output scores: "+ scores.length);
//                for(float score: scores){
//                    System.out.println(score);
//                }

                //getting the max score index:
                float maxScore = -Float.MAX_VALUE;
                int maxScoreIdx = -1;
                for (int j = 0; j < scores.length; j++) {
                    System.out.println(scores[j]);
                    if (scores[j] > maxScore) {
                        maxScore = scores[j];
                        maxScoreIdx = j;
                    }
                }
                System.out.println("idx"+maxScoreIdx);

                String classes[] = new String[] {"happy", "sad"};
                String className = classes[maxScoreIdx];
                textView.setText("Image matches: " + className);
                System.out.println("Image matches: " + className);

//        https://stackoverflow.com/questions/8748444/passing-strings-between-activities-in-android
                Intent intent = new Intent(getApplicationContext(),memeFromImage.class);
                intent.putExtra("IMG-ID",imgId);
                intent.putExtra("ByteArray",bs.toByteArray());
                intent.putExtra("CLASS_NAME",className);
                startActivity(intent);
            }

        });
    }

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);


        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}