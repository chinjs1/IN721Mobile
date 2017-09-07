package bit.chinjs1.practical12b_resources;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class resources extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        // Created the resource obj
        Resources resourceResolver = getResources();

        // Return an array of int
        int datesArray[] = resourceResolver.getIntArray(R.array.FebFridays);

        // Get a reference pointer to the target TextView(called heading)
        TextView heading = (TextView)findViewById(R.id.heading);

        // Pass the resource ID of the string constant you want (stored in R) to the Resource obj
        String febFridays = "February Fridays are on: ";

        // Keep looping over the array until it's done
        for (int i = 0; i < datesArray.length; i++)
        {
            febFridays += datesArray[i] + " ";
        }

        // Set it's text
        heading.setText(febFridays);

    }
}
