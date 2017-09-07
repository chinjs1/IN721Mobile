package bit.chinjs1.languagetrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // A button to restart the quiz
        Button restartQuiz = (Button) findViewById(R.id.btnRestartQuiz);
        restartQuiz.setOnClickListener(new restartApp());

        // A button to end the quiz
        Button quitQuiz = (Button) findViewById(R.id.btnQuitQuiz);
        quitQuiz.setOnClickListener(new quitApp());

        // Get the results
        Intent resultsIntent = getIntent();
        int score = resultsIntent.getIntExtra("results", -1);

        // Output the final score
        TextView tvFinalScore = (TextView) findViewById(R.id.tvOutputScore);
        tvFinalScore.setText(score + " out of 11");
    } // End onCreate

    // Resart the app
    private class restartApp implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            finish();
            System.exit(0);
        } // End onClick
    } // End restartApp

    private class quitApp implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            finish();
            moveTaskToBack(true);
        } // End onClick
    } // End quitApp
} // End ResultActivity
