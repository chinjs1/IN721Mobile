package bit.chinjs1.welcometodunedin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    // Declare a ListView
    ListView activityGroupListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make a ListView with items in it
        setUpActivityList();

        // Get the ListView's id
        ListView activityGroupListView = (ListView) findViewById(R.id.lvNavigation);

        // Allow the ListView to use an onItemClickListener
        activityGroupListView.setOnItemClickListener(new ActivityGroupNavListClickHandler());
    }

    public void setUpActivityList() {

        // Populate the ListView with these strings
        String[] groups = {"Activities", "Shopping", "Dining", "Services"};

        // Create an Adapter
        ArrayAdapter<String> activityGroupAdapter = new ArrayAdapter<String>(this, R.layout.activities_group_list_item, groups);

        // Setting the adapter to the ListView
        activityGroupListView = (ListView) findViewById(R.id.lvNavigation);
        activityGroupListView.setAdapter(activityGroupAdapter);

        // activityGroupListView.setAdapter(activityGroupAdapter);
    }

    private class ActivityGroupNavListClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String clickedItem = (String) activityGroupListView.getItemAtPosition(position).toString();
            Intent goToIntent;

            switch (clickedItem) {
                case "Activities":
                    goToIntent = new Intent(MainActivity.this, activitiesActivity.class);
                    break;

                case "Shopping":
                    goToIntent = new Intent(MainActivity.this, shoppingActivity.class);
                    break;

                case "Dining":
                    goToIntent = new Intent(MainActivity.this, diningActivity.class);
                    break;

                case "Services":
                    goToIntent = new Intent(MainActivity.this, servicesActivity.class);
                    break;
                default:
                    goToIntent = null;
            } // End switch

            if(goToIntent != null)
                startActivity(goToIntent);

        } // End onItemCLick
    }// End ActivityGroupNavListClickHandler
}
