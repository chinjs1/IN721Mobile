package com.example.op_bit.alertbuilder;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    SimpleConfirm confirmPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the click handler for the order button.
        Button orderPizza = (Button) findViewById(R.id.btnOrder);
        orderPizza.setOnClickListener(new buttonOrderHandler());

    }

    // Find out if the user clicked yes or no.
    // If Yes - set the img to Pizza, else - set to a sad face.
    public void getConfirmPizza(boolean selectPizza) {

        // Get the img
        ImageView chosenImg = (ImageView) findViewById(R.id.ivPizza);

        if (selectPizza) {

            chosenImg.setImageResource(R.drawable.pizza);

        } else {
            chosenImg.setImageResource(R.drawable.sad_face);
        }
    }

    public class buttonOrderHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            confirmPizza = new SimpleConfirm();
            FragmentManager fm = getFragmentManager();
            confirmPizza.show(fm, "confirm");

        }
    }
}
