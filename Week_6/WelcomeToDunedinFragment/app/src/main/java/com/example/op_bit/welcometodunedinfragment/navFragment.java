package com.example.op_bit.welcometodunedinfragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class navFragment extends Fragment {


    public navFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_nav, container, false);

        ListView listServices = (ListView) fragmentView.findViewById(R.id.lvNav);

        // Populate the ListView with these strings
        String[] groups = {"activities", "shopping", "dining", "octogon"};

        // Create an Adapter
        ArrayAdapter<String> activityGroupAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, groups);

        // Setting the adapter to the ListView
        listServices.setAdapter(activityGroupAdapter);
        listServices.setOnItemClickListener(new servicesListHandler());

        return fragmentView;

       // return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    private class servicesListHandler implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String clickedItem = (String) parent.getItemAtPosition(position).toString().toLowerCase();

            MainActivity myActivity = (MainActivity) getActivity();
            myActivity.giveMeMyData(clickedItem);
        }
    }
}
