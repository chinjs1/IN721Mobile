package com.example.op_bit.dialogfragment;


import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


/**
 */
public class selectImage extends DialogFragment {


    public selectImage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_select_image, container, false);

        ListView lvNbaImages = (ListView) fragmentView.findViewById(R.id.lvNba);

        String[] players = {"LeBron", "Wade", "Kobe", "Shaq", "Jordan"};

        ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, players);

        lvNbaImages.setAdapter(playerAdapter);

        lvNbaImages.setOnItemClickListener(new listViewItemClickHandler());

        return fragmentView;

       // return inflater.inflate(R.layout.fragment_select_image, container, false);
    }

    private class listViewItemClickHandler implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String clickedItem = (String) parent.getItemAtPosition(position).toString().toLowerCase();

            MainActivity myActivity = (MainActivity) getActivity();
            myActivity.giveMeMyData(clickedItem);

        }
    }
}
