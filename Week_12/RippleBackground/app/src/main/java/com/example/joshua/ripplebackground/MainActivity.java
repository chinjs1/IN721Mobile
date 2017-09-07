package com.example.joshua.ripplebackground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skyfishjy.library.RippleBackground;

public class MainActivity extends AppCompatActivity {

    RippleBackground ripple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ripple = (RippleBackground) findViewById(R.id.rippleBackground);

        Button button = (Button) findViewById(R.id.rippleBtn);
        button.setOnClickListener(new RippleOnButtonClick());
    }

    private class RippleOnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(ripple.isRippleAnimationRunning()) {
                ripple.stopRippleAnimation();
            }
            else {
                ripple.startRippleAnimation();
            }
        }
    }
}
