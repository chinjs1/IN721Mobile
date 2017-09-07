package com.example.op_bit.fragmentintro;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class listFragment extends Fragment {


    public listFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView lvNbaPlayers = (ListView) fragmentView.findViewById(R.id.lvNba);

        String[] players = {"LeBron", "Wade", "Kobe", "Shaq", "Jordan", "Magic", "Wilt", "KAJ"};
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, players);

        lvNbaPlayers.setAdapter(playerAdapter);

        return fragmentView;
        //return inflater.inflate(R.layout.fragment_list, container, false);
    }

}
