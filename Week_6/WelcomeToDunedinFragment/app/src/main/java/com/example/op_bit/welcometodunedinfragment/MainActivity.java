package com.example.op_bit.welcometodunedinfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // Global vars
    FragmentManager fm;
    navFragment chosenListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init the fragments
        Fragment listFragment = new navFragment();
        Fragment imgFragment = new contentsFragment();
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.listLayout, listFragment);
        ft.replace(R.id.imgLayout, imgFragment);
        ft.commit();

    }

    // The method for getting the required data.
    public void giveMeMyData(String chosenNav){

        // In the drawable folder, find the name passed in that matches the drawable image name and store it in an int var.
        int imageId = getResources().getIdentifier(chosenNav, "drawable", getPackageName());

        // Create a bundle
        Bundle bundle = new Bundle();

        // Bundle the string and the int.
        // Bundle up the data to give to the fragment
        bundle.putString("title", chosenNav);
        bundle.putInt("imageId", imageId);

        Fragment imgFragment = new contentsFragment();
        imgFragment.setArguments(bundle);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.imgLayout, imgFragment);
        ft.commit();


    }
}
