package com.example.joshua.viewanimations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class MainActivity extends AppCompatActivity {

    ImageView ivLbj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivLbj = (ImageView) findViewById(R.id.ivLbj);
        ivLbj.setOnClickListener(new LeBronImageClickHandler());
    }

    private class LeBronImageClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            YoYo.with(Techniques.StandUp).playOn(ivLbj);
        }
    }
}
