package bit.chinjs1.multipleactivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        Button btnChangeActivityToWebsite = (Button) findViewById(R.id.btnC);
        btnChangeActivityToWebsite.setOnClickListener(new changeActivityButtonListenerToWebsite());
    }

    private class changeActivityButtonListenerToWebsite implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent goToWebsite = new Intent("android.intent.action.VIEW", Uri.parse("http://www.nba.com/"));
            startActivity(goToWebsite);
        }
    }
}
