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



        try {
            // kitten.jpg should be changed to selectedImageUri
            module = Module.load(assetFilePath(this, "image_model.pt"));
            textView.setText("module");
        }catch(IOException e){
            Log.e("PTRTDryRun", "Error reading assets", e);
            textView.setText("rip module");
            finish();
        }

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { imageChooser(); }
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

                textView.setTextSize(25);
                textView.setText("line 74");

                textView.setText("line 81");

//                try {
//                    // kitten.jpg should be changed to selectedImageUri
//                    bitmap = BitmapFactory.decodeStream(getAssets().open("kitten.jpg"));
//                    textView.setText("bitmap");
//                }catch(IOException e){
//                    Log.e("PTRTDryRun", "Error reading assets", e);
//                    textView.setText("rip bitmap");
//                    finish();
//                }

                textView.setText("line 93");

                final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor
                        (bitmap, TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);
                final Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
                final float[] scores = outputTensor.getDataAsFloatArray();
                Float maxScore = - Float.MAX_VALUE;
                int maxScoreIdx = -1;
                for (int j = 0; j < 4; j++) {
                    if (scores[j] > maxScore) {
                        maxScore = scores[j];
                        maxScoreIdx = j;
                    }
                }
                textView.setText("line 108");

                String classes[] = new String[] {"angry", "happy", "sad", "sleepy"};
//                textView.setText(String.valueOf(maxScoreIdx));
                String className = classes[maxScoreIdx];

                textView.setText(className);

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