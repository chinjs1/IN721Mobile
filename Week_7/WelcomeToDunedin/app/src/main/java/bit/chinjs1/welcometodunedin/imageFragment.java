package bit.chinjs1.welcometodunedin;


import android.os.Bundle;
import android.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import bit.chinjs1.welcometodunedin.R;


public class imageFragment extends DialogFragment {


    public imageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the view..
        View fragmentView = inflater.inflate(R.layout.fragment_image, container, false);

        // Get the bundle from the handler.
        Bundle unpackBundle = getArguments();

        // once the bundled is unpacked, grab its image
        int imageID = unpackBundle.getInt("imageID");
        ImageView imgView = (ImageView) fragmentView.findViewById(R.id.ivClickedImage);
        imgView.setImageResource(imageID);

        return fragmentView;

        // return inflater.inflate(R.layout.fragment_image, container, false);
    }

}
