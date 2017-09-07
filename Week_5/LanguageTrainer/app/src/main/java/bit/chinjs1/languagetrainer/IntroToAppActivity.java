package bit.chinjs1.languagetrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroToAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the button obj
        Button startQuestionActivity = (Button) findViewById(R.id.btnToStartApp);
        startQuestionActivity.setOnClickListener(new startQuestions());

    } // End onCreate

    private class startQuestions implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Create an intent to go start the app
            Intent changeActivityIntent = new Intent(IntroToAppActivity.this, QuestionActivity.class);
            startActivity(changeActivityIntent);

        } // End onClick
    } // End startQuestions
} // End IntroToAppActivity
