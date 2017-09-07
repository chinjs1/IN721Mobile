package com.example.op_bit.welcometodunedinfragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.op_bit.welcometodunedinfragment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class contentsFragment extends Fragment {


    public contentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_contents, container, false);

        // Unpack the bundle in this fragment
        Bundle unpackBundle = getArguments();

        // If there is data in the bundle then run the following:
        if(unpackBundle != null) {

            // Find the title and the image
            String title = unpackBundle.getString("title");
            int imgId = unpackBundle.getInt("imageId");


            // Change the title and image to match the id's
            TextView tvTitle = (TextView) fragmentView.findViewById(R.id.tvTitle);
            tvTitle.setText(title);

            ImageView image = (ImageView) fragmentView.findViewById(R.id.ivSelectedImg);
            image.setImageResource(imgId);
        }

        return fragmentView;

        // return inflater.inflate(R.layout.fragment_contents, container, false);
    }

}
