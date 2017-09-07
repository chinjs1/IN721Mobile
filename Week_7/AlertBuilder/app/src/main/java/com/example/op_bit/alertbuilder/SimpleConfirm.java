package com.example.op_bit.alertbuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

public class SimpleConfirm extends DialogFragment {


    public SimpleConfirm() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setIcon(R.drawable.ic_pizza);

        alertBuilder.setTitle("Do you want to order Pizza?");

        alertBuilder.setPositiveButton("Yes", new YesToPizzaHandler());
        alertBuilder.setNegativeButton("No", new NoToPizzaHandler());

        return alertBuilder.create();

    }

    private class YesToPizzaHandler implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            MainActivity home = (MainActivity)getActivity();
            home.getConfirmPizza(true);
        }
    }

    private class NoToPizzaHandler implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            MainActivity home = (MainActivity)getActivity();
            home.getConfirmPizza(false);

        }
    }
}
