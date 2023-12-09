package com.zybooks.mymathmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        // Retrieve data from intent
        int correctAnswers = getIntent().getIntExtra("CorrectAnswers", 0);
        int incorrectAnswers = getIntent().getIntExtra("IncorrectAnswers", 0);

        // Calculate total questions and percentage
        int totalQuestions = correctAnswers + incorrectAnswers;
        double percentage = totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;

        // Set text views
        TextView correctView = findViewById(R.id.numCorrectNum);
        TextView incorrectView = findViewById(R.id.numIncorrectNum);
        TextView percentView = findViewById(R.id.PercentNum);

        correctView.setText(String.valueOf(correctAnswers));
        incorrectView.setText(String.valueOf(incorrectAnswers));
        percentView.setText(String.format(Locale.getDefault(), "%.1f", percentage));
    }

    // Method for "Back to Home" button
    public void backToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Close this activity
    }
}
