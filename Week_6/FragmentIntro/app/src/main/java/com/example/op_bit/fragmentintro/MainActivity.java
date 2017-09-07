package com.example.op_bit.fragmentintro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landscape);

        // Create the button handler for the image.
        // Button imageFragment = (Button) findViewById(R.id.btnFragmentImage);
        // imageFragment.setOnClickListener(new fragmentLoadImage());

        // Create the button handler for the list view
        // Button listFragment = (Button) findViewById(R.id.btnFragmentList);
        // listFragment.setOnClickListener(new fragmentLoadList());

        Button bothImage = (Button) findViewById(R.id.ivButton);
        bothImage.setOnClickListener(new bothImageFragment());

        Button bothList = (Button) findViewById(R.id.lvButton);
        bothList.setOnClickListener(new bothListFragment());

    }


    private class fragmentLoadImage implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Fragment ivFragment = new imageFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.linearLayoutFragment, ivFragment);
            ft.commit();

        }
    }

    private class fragmentLoadList implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Fragment lvFragment = new listFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.linearLayoutFragment, lvFragment);
            ft.commit();

        }
    }

    private class bothImageFragment implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Fragment ivFragment = new imageFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.imageLayout, ivFragment);
            ft.commit();

        }
    }

    private class bothListFragment implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Fragment ivFragment = new listFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.listLayout, ivFragment);
            ft.commit();
        }
    }
}
