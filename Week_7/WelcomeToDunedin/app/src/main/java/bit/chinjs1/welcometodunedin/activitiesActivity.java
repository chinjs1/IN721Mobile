package bit.chinjs1.welcometodunedin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class activitiesActivity extends AppCompatActivity {

    // Globals
    FragmentManager fm;
    imageFragment chosenItem;
    Places[] placesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        // Initialise and create the needed data.
        initialiseDataArray();

        // Init the adapter.
        PlacesArrayAdapter placesAdapter = new PlacesArrayAdapter(this, R.layout.adapterlayout, placesArray);

        // Create the listview and give it the adapter.
        ListView lvImages = (ListView) findViewById(R.id.lvPlaces);
        lvImages.setAdapter(placesAdapter);

        // Make the fragment and manager.
        Fragment listFragment = new imageFragment();
        fm = getFragmentManager();

        // Create the click handler for the image fragment.
        lvImages.setOnItemClickListener(new imageFragmentHandler());

    }

    public class PlacesArrayAdapter extends ArrayAdapter<Places> {

        public PlacesArrayAdapter(Context context, int resource, Places[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            // Inflate the activity.
            LayoutInflater inflater = LayoutInflater.from(activitiesActivity.this);

            // Make a customview using the new adapter layout
            View customView = inflater.inflate(R.layout.adapterlayout, container, false);

            // Grab the ID if the customviews image and textview.
            ImageView itemImageView = (ImageView) customView.findViewById(R.id.ivGroups);
            TextView itemTextView = (TextView) customView.findViewById(R.id.tvPlaces);

            // Find the items position and populate the image and textview with the appropiate things.
            Places currentItem = getItem(position);
            itemImageView.setImageDrawable(currentItem.placeImage);
            itemTextView.setText(currentItem.toString());

            return customView;
        }
    }

    private void initialiseDataArray() {

        Resources resourceMachine = getResources();
        Drawable artImage = resourceMachine.getDrawable(R.drawable.art, null);
        Drawable meridianImage = resourceMachine.getDrawable(R.drawable.meridian, null);
        Drawable museumImage = resourceMachine.getDrawable(R.drawable.otagomuseum, null);
        Drawable railwayImage = resourceMachine.getDrawable(R.drawable.railway, null);

        placesArray = new Places[4];
        placesArray[0] = new Places("Art Gallery", "14 Moray Place", artImage, R.drawable.art);
        placesArray[1] = new Places("Meridian Mall", "134 George St", meridianImage, R.drawable.meridian);
        placesArray[2] = new Places("Otago Musem", "77 Albion Place", museumImage, R.drawable.otagomuseum);
        placesArray[3] = new Places("Railway Station", "423 High St", railwayImage, R.drawable.railway);

    }

    private class imageFragmentHandler implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Places selectedPlace = (Places)parent.getItemAtPosition(position);
            int imageId = selectedPlace.getImgID();

            // Bundle up the data.
            Bundle bundleImage = new Bundle();
            bundleImage.putInt("imageID", imageId);

            imageFragment imgFragment = new imageFragment();
            imgFragment.setArguments(bundleImage);
            imgFragment.show(fm, "");
        }
    }
}
