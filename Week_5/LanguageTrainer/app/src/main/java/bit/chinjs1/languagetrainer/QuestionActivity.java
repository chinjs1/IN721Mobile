package bit.chinjs1.languagetrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    // Global Variables
    ArrayList<Question> words = new ArrayList<>();
    private final int MAX_QUESTIONS = 11;
    Question currentQuestion;
    private int questionNumber;
    private int score;
    private RadioButton rbDas;
    private RadioButton rbDer;
    private RadioButton rbDie;
    private Button submitAnswer;
    public String correct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        rbDas = (RadioButton) findViewById(R.id.rbDas);
        rbDer = (RadioButton) findViewById(R.id.rbDer);
        rbDie = (RadioButton) findViewById(R.id.rbDie);

        // Create the button obj for submitting
        Button submitAnswer = (Button) findViewById(R.id.btnSubmit);
        submitAnswer.setOnClickListener(new submitAnswerListener());

        // Give the variables some data
        questionNumber = 0;
        currentQuestion = null;
        score = 0;

        // Create the questions and then show them as soon as the app creates
        setUpQuestions();
        showQuestions();

    } // End onCreate

    public void setUpQuestions() {

        // An arraylist of words with the image to match
        words.add(new Question("Apfel", "Der", R.drawable.der_apfel));
        words.add(new Question("Auto", "Das", R.drawable.das_auto));
        words.add(new Question("Baum", "Der", R.drawable.der_baum));
        words.add(new Question("Ente", "Die", R.drawable.die_ente));
        words.add(new Question("Hexe", "Die", R.drawable.die_hexe));
        words.add(new Question("Haus", "Das", R.drawable.das_haus));
        words.add(new Question("Kuh", "Die", R.drawable.die_kuh));
        words.add(new Question("Milch", "Die", R.drawable.die_milch));
        words.add(new Question("Stuhl", "Der", R.drawable.der_stuhl));
        words.add(new Question("Schloss", "Der", R.drawable.der_schloss));
        words.add(new Question("Schaf", "Das", R.drawable.das_schaf));
    } // End setUpQuestions

    public void showQuestions() {

        // Create a random
       // Random randWord = new Random();

        // Make an int var called wordNum which holds 11 - the amount of questions we have
       // int[] wordNum = new int[MAX_QUESTIONS];

        // Built in shuffle method to randomize the order of arraylist
         Collections.shuffle(words);

        // Store the question number into currentQuestion
        currentQuestion = words.get(questionNumber);

        // Start the first question as '1'
        String qNum = "Question: " + (questionNumber + 1);

        // Set a random image into the ImageView
        ImageView ivCurrentQuestionImage = (ImageView) findViewById(R.id.ivVisualNoun);
        ivCurrentQuestionImage.setImageResource(currentQuestion.getImage());
       // ivCurrentQuestionImage.setImageDrawable(getResources().getDrawable(words.get(0).image));

        // Set the question num in the TextView
        TextView question = (TextView) findViewById(R.id.tvTitleQuestionNum);
        question.setText(qNum);


    } // End showQuestions

    private boolean isAnswerCorrect() {

        // Create the radio group ID
        RadioGroup rgAnswers = (RadioGroup) findViewById(R.id.rgNoun);

        // Get the checked radio button
        int checkedId = rgAnswers.getCheckedRadioButtonId();

        // Declaring a radio button with the checkedId
        RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);

        // Check if the radio button matches the current question
        if (checkedRadioButton.getText().toString().equals(currentQuestion.getArticle())) {
            return true;
        } else {
            return false;
        }
    } // End isAnswerCorrect

    private void changeTextColorGreen(String correct) {

        switch (correct) {

            case "Das":
                rbDas.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;

            case "Der":
                rbDer.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;

            case "Die":
                rbDie.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
        }

    }

    private class submitAnswerListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            
            // Create the radio group ID
            RadioGroup rgAnswers = (RadioGroup) findViewById(R.id.rgNoun);

            // Get the checked radio button
            int checkedId = rgAnswers.getCheckedRadioButtonId();

            // Declaring a radio button with the checkedId
            RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);

            correct = checkedRadioButton.toString();

            // If the answer is correct, let the user know.. else, tell them they are wrong
            boolean correctAnswer = isAnswerCorrect();
            //String answer = submitAnswer.getText().toString();
            if (correctAnswer == true) {

               // changeTextColorGreen(correct);
                Toast.makeText(QuestionActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
               // submitAnswer.setBackgroundColor(getResources().getColor(R.color.colorGreen));
               // rbDer.setBackgroundColor(getResources().getColor(R.color.colorGreen));
               // rbDas.setBackgroundColor(getResources().getColor(R.color.colorGreen));
               // rbDie.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                score++;
                questionNumber++;
            } // End if
            else {
               // submitAnswer.setBackgroundColor(getResources().getColor(R.color.colorRed));
                Toast.makeText(QuestionActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
               // rbDer.setBackgroundColor(getResources().getColor(R.color.colorRed));
               // rbDas.setBackgroundColor(getResources().getColor(R.color.colorRed));
               // rbDie.setBackgroundColor(getResources().getColor(R.color.colorRed));
                questionNumber++;
            } // End else

            // If the quiz has not reached the last question
            if (questionNumber < words.size()) {

                // Check if the user has answered correctly and show more questions
                isAnswerCorrect();
                showQuestions();

            } // End if
            else {

                // Else, change activity and output the users final score
                Intent changeActivity = new Intent(QuestionActivity.this, ResultActivity.class);
                changeActivity.putExtra("results", score);
                startActivity(changeActivity);
            } // End else
        } // End onClick
    } // End submitAnswerListener
} // End QuestionActivity
