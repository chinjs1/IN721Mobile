package com.example.joshua.explodingimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.easyandroidanimations.library.ExplodeAnimation;

public class MainActivity extends AppCompatActivity {

    ImageView ivLbj;
    ExplodeAnimation explodeLbj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivLbj = (ImageView)findViewById(R.id.ivLbj);
        ivLbj.setOnClickListener(new ivExplodeClickHandler());
        explodeLbj = new ExplodeAnimation(ivLbj);

    }

    private class ivExplodeClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            explodeLbj.animate();
        }
    }
}
