package bit.chinjs1.welcometodunedindrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class diningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_layout);

        //Declaring a TextView and ImageView. Getting there ids
        TextView subScreenTitle = (TextView) findViewById(R.id.tvTitle);
        ImageView subScreenImage = (ImageView) findViewById(R.id.ivScreenImage);

        //Setting text and image for the TextView and ImageView
        subScreenTitle.setText("Dining");
        subScreenImage.setImageResource(R.drawable.dunedin);


    }
}
