package com.example.joshua.photomosaic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    File mPhotoFile;
    Uri mPhotoFileUri;
    String mPhotoFileName;
    Button btnStart;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup button and images
        SetupScreenReferences();
    }

    // Init all the screen references here
    public void SetupScreenReferences() {

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new CameraButtonClickHandler());
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
    }

    public void DisplayImages(Bitmap img) {

        iv1.setImageBitmap(img);
        iv2.setImageBitmap(img);
        iv3.setImageBitmap(img);
        iv4.setImageBitmap(img);
    }

    // Run this code on click
    private class CameraButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            mPhotoFile = createTimeStampedFile();
            mPhotoFileUri = Uri.fromFile(mPhotoFile);
            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoFileUri);
            startActivityForResult(imageCaptureIntent, 1);
        }
    }

    // Make a jpg timestamp
    private File createTimeStampedFile() {

        // Fetch system image folder
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Make subdirectory
        File imageStorageDirectory = new File(imageRootPath, "PhotoMosaic");
        if(!imageStorageDirectory.exists()) {

            imageStorageDirectory.mkdirs();
        }

        // Get the timestamp
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date currentTime = new Date();
        String timeStamp = timeStampFormat.format(currentTime);

        // Make the file
        mPhotoFileName = "IMG_" + timeStamp + ".jpg";
        File photoFile = new File(imageStorageDirectory.getPath() + File.separator + mPhotoFileName);
        return photoFile;
    }

    // Callback after photo is taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check its the correct return
        if (requestCode ==1) {

            // Check if it is OK
            if (resultCode == RESULT_OK) {

                // We need the filepath for BitmapFactory, not the file
                String realFilePath = mPhotoFile.getPath();

                // Create BitMap
                Bitmap userPhotoBitmap = BitmapFactory.decodeFile(realFilePath);

                DisplayImages(userPhotoBitmap);
            }
            else {

                Toast.makeText(this, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
            }
        }
    } // End onActivityResult
}
