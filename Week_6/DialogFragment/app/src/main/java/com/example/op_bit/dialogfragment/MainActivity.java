package com.example.op_bit.dialogfragment;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

            selectImage chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dialogHandler = (Button) findViewById(R.id.btnSelectImage);
        dialogHandler.setOnClickListener(new dialogFragmentHandler());
    }

    public void giveMeMyData(String chooseImage){

        chosenImage.dismiss();

        // In the drawable folder, find the name passed in that matches the drawable image name.
        int imageId = getResources().getIdentifier(chooseImage, "drawable", getPackageName());

        RelativeLayout mainActivityLayout = (RelativeLayout) findViewById(R.id.activity_main);
        mainActivityLayout.setBackground(getResources().getDrawable(imageId));

    }

    private class dialogFragmentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            chosenImage = new selectImage();
            FragmentManager fm = getFragmentManager();
            chosenImage.show(fm, "confirm");

        }
    }
}
